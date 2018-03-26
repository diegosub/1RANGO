import {Component} from '@angular/core';
import {IonicPage, NavController, ToastController, AlertController, LoadingController, ViewController} from 'ionic-angular';
import {FavoritoProvider} from '../../providers/rest/favorito';


@IonicPage()
@Component({
    selector: 'page-favorito',
    templateUrl: 'favorito.html'
})
export class FavoritoPage {
    favouriteItems: any[] = [];
    Cart:any[];
    noOfItems:number;
    idUsuario: any;
    idEstabelecimento: any;
    public Favoritos: Array<any> = [];

    constructor(public navCtrl: NavController,
                public loadingCtrl: LoadingController,
                private alertCtrl: AlertController,
                private view: ViewController,
                public toastCtrl: ToastController,
                private favoritoProvider: FavoritoProvider) {

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {

          this.idUsuario = Number(localStorage.getItem('uid'));

          this.favoritoProvider.getAll(this.idUsuario)
            .then((data: any) => {
              this.Favoritos = [];

              for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                this.Favoritos.push(obj);
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
        })
    }

    ionViewWillEnter(){
      let carrinho: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));
      this.noOfItems=carrinho!=null ? carrinho.length : null;
    }

    isFavourite(): boolean {
        if (this.Favoritos.length == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    navigate(idEstabelecimento) {
      let carrinho: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));

      if(carrinho != null && carrinho[0].item.idEstabelecimento != idEstabelecimento) {
        this.navCtrl.push("VerificarCarrinhoPage");
      }
      else{
        this.navCtrl.push("CardapioPage", {idEstabelecimento: idEstabelecimento});
      }
    }

    navCidade() {
      this.navCtrl.push("CidadePage");
    }

    navcart() {
        this.navCtrl.push("CarrinhoPage");
    }

    ionViewDidLoad() {
       this.view.setBackButtonText('');
    }

}
