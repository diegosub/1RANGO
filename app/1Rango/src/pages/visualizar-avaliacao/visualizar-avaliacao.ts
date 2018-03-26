import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController, LoadingController, AlertController } from 'ionic-angular';
import { AvaliacaoProvider } from '../../providers/rest/avaliacao'

@IonicPage()
@Component({
  selector: 'page-visualizar-avaliacao',
  templateUrl: 'visualizar-avaliacao.html',
})
export class VisualizarAvaliacaoPage {

  idEstabelecimento: any;
  public Avaliacoes: Array<any> = [];

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public loadingCtrl: LoadingController,
              private alertCtrl: AlertController,
              private avaliacaoProvider: AvaliacaoProvider,
              private view: ViewController) {

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        this.idEstabelecimento = this.navParams.get('idEstabelecimento');

        loader.present().then(() => {
          this.avaliacaoProvider.getAvaliacoes(this.idEstabelecimento)
            .then((data: any) => {
              this.Avaliacoes = [];
              loader.dismiss();

              for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                this.Avaliacoes.push(obj);
              }

            })
            .catch((error: any) => {
              let alert = this.alertCtrl.create({
                title: "Ops...",
                subTitle: "Não foi possível estabelecer uma conexão com o servidor.",
                buttons: ["Ok"]
              });

              alert.present();
              loader.dismiss();
            });
        })

  }

  closeModal() {
    this.view.dismiss();
  }

}
