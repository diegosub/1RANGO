import {Component} from '@angular/core';
import {IonicPage, NavController, LoadingController} from 'ionic-angular';
import {AlertController} from 'ionic-angular';
import {CidadeProvider} from '../../providers/rest/cidade'

@IonicPage()
@Component({
    selector: 'page-cidade',
    templateUrl: 'cidade.html'
})
export class CidadePage {

    noOfItems: any;
    public Cidades: Array<any> = [];

    constructor(public navCtrl: NavController,
                public loadingCtrl: LoadingController,
                private alertCtrl: AlertController,
                private cidadeProvider: CidadeProvider) {

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {
          this.cidadeProvider.getAll()
            .then((data: any) => {
              this.Cidades = [];
              loader.dismiss();

              for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                this.Cidades.push(obj);
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
              this.navCtrl.push("HomePage");
            });
        })
    }

    ionViewWillEnter(){
      let cart: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));
      this.noOfItems=cart!=null ? cart.length : null;
    }

    navigate(idCidade) {
        this.navCtrl.push("EstabelecimentoPage", {idCidade: idCidade});
    }

    navcart() {
        this.navCtrl.push("CarrinhoPage");
    }

}
