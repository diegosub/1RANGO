import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController, AlertController, LoadingController  } from 'ionic-angular';
import {EstabelecimentoProvider} from '../../providers/rest/estabelecimento'


@IonicPage()
@Component({
  selector: 'page-modal-informacoes',
  templateUrl: 'modal-informacoes.html',
})
export class ModalInformacoesPage {

  public idEstabelecimento: any;
  public Estabelecimento: any;
  public Horarios: Array<any> = [];
  public dsVlMinimo: any;
  public dsTipoEntrega: any;
  public dsDelivery: any;
  public dsRetirada: any;
  public dsEntrega: any;
  public dsFormaPagamentoDelivery: any;
  public dsFormaPagamentoRetirada: any;
  public dsBandeiraDelivery: any;
  public dsBandeiraRetirada: any;
  public fgCartao: any;
  public fgDelivery: any;
  public fgRetirada: any;


  constructor(public navCtrl: NavController,
              public loadingCtrl: LoadingController,
              private view: ViewController,
              private estabelecimentoProvider: EstabelecimentoProvider,
              private alertCtrl: AlertController,
              public navParams: NavParams) {

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {
          this.idEstabelecimento = this.navParams.get('idEstabelecimento');
          this.estabelecimentoProvider.getInfoEstabelecimento(this.idEstabelecimento)
            .then((data: any) => {
              this.Estabelecimento = data;

              this.dsVlMinimo = this.Estabelecimento.dsVlMinimo;
              this.dsTipoEntrega = this.Estabelecimento.dsTpEntrega;
              this.dsDelivery = this.Estabelecimento.dsDelivery;
              this.dsRetirada = this.Estabelecimento.dsRetirada;
              this.dsEntrega = this.Estabelecimento.dsEntrega;
              this.dsFormaPagamentoDelivery = this.Estabelecimento.dsFormaPagamentoDelivery;
              this.dsFormaPagamentoRetirada = this.Estabelecimento.dsFormaPagamentoRetirada;
              this.dsBandeiraDelivery = this.Estabelecimento.dsBandeiraDelivery;
              this.dsBandeiraRetirada = this.Estabelecimento.dsBandeiraRetirada;
              this.fgCartao = this.Estabelecimento.fgCartao;
              this.fgDelivery = this.Estabelecimento.fgDelivery;
              this.fgRetirada = this.Estabelecimento.fgRetiradaLocal;

              this.Horarios = [];

              if(data.listaHorario != null) {
                for (var i = 0; i < data.listaHorario.length; i++) {
                  var obj = data.listaHorario[i];
                  this.Horarios.push(obj);
                }
              }

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
        })

        loader.dismiss();
  }

  closeModal() {
    this.view.dismiss();
  }

}
