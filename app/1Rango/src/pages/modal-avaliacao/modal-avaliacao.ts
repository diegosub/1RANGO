import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController, AlertController, LoadingController} from 'ionic-angular';
import {AvaliacaoProvider} from '../../providers/rest/avaliacao';


@IonicPage()
@Component({
  selector: 'page-modal-avaliacao',
  templateUrl: 'modal-avaliacao.html',
})
export class ModalAvaliacaoPage {

  valorAvaliacao: Number;
  observacao: any;
  idPedido: any;
  Avaliacao: any;
  idEstabelecimento: any;

  public objeto = {
    vlAvaliacao: 0,
    dsAvaliacao: String,
    idEstabelecimento: 0,
    idPedido: 0
  }

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public loadingCtrl: LoadingController,
              private view: ViewController,
              public avaliacaoProvider: AvaliacaoProvider,
              private alertCtrl: AlertController) {

        this.valorAvaliacao = Number(0);
        this.observacao = '';
        this.idPedido = this.navParams.get('idPedido');
        this.idEstabelecimento = this.navParams.get('idEstabelecimento');
  }

  select(num) {
    this.valorAvaliacao = num;
  }

  avaliar() {
    let msg = this.validar();

    if(msg != null && msg != '') {

      let alert = this.alertCtrl.create({
        title: "Atenção",
        subTitle: msg,
        buttons: ["Ok"]
      });
      alert.present();

    } else {

      //CONCLUIR AVALIACAO

      let loader = this.loadingCtrl.create({
          content: "Aguarde...",
      });

      loader.present().then(() => {

        this.objeto.vlAvaliacao = Number(this.valorAvaliacao);
        this.objeto.dsAvaliacao = this.observacao;
        this.objeto.idPedido = Number(this.idPedido);
        this.objeto.idEstabelecimento = Number(this.idEstabelecimento);

        this.avaliacaoProvider.cadastrar(this.objeto)
          .then((data: any) => {

            this.Avaliacao = data;

            if(this.Avaliacao.mensagem != null
                && this.Avaliacao.mensagem != '')
            {
              let msg = this.alertCtrl.create({
                title: "Atenção!",
                subTitle: this.Avaliacao.mensagem,
                buttons: ["Ok"]
              });

              msg.present();
            }
            else
            {
              let msg = this.alertCtrl.create({
                title: "Sucesso!",
                subTitle: "Sua avaliação foi realizada.",
                buttons: ["Ok"]
              });

              this.closeModal();
              msg.present();
            }

            loader.dismiss();

          })
          .catch((error: any) => {
            let alert = this.alertCtrl.create({
              title: "Ops...",
              subTitle: "Não foi possível estabelecer uma conexão com o servidor.",
              buttons: ["Ok"]
            });

            loader.dismiss();
            alert.present();
            this.navCtrl.push("HomePage");
          });
        });
    }
  }

  validar() {
    let msg = null;

    if(this.valorAvaliacao == null || this.valorAvaliacao == 0) {
      msg = 'Dê uma nota para o estabelecimento de acordo com o atendimento do seu pedido (min: 1, max: 5).';
      return msg;
    }

    if(this.observacao == null || this.observacao == '') {
      msg = 'Deixe seu comentário sobre o atendimento.';
      return msg;
    }

    return msg;
  }

  closeModal() {
    this.view.dismiss();
  }

}
