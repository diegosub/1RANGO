import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController, LoadingController, AlertController } from 'ionic-angular';
import {CidadeProvider} from '../../providers/rest/cidade'


@IonicPage()
@Component({
  selector: 'page-modal-bairro',
  templateUrl: 'modal-bairro.html',
})
export class ModalBairroPage {

  idEstabelecimento: any;
  public Bairros: Array<any> = [];

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public loadingCtrl: LoadingController,
              private cidadeProvider: CidadeProvider,
              private alertCtrl: AlertController,
              private view: ViewController) {

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        this.idEstabelecimento = this.navParams.get('idEstabelecimento');

        loader.present().then(() => {
          this.cidadeProvider.getCidadeBairro(this.idEstabelecimento)
            .then((data: any) => {
              this.Bairros = [];
              loader.dismiss();

              for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                this.Bairros.push(obj);
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

  selecionar(idBairro, dsBairro, strValorTaxa, vlTaxa) {
    let objRetorno = {idBairro: idBairro, dsBairro: dsBairro, strValorTaxa: strValorTaxa, vlTaxa: vlTaxa};
    this.view.dismiss(objRetorno);
  }

  closeModal() {
    this.view.dismiss();
  }

}
