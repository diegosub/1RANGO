import {Component, ViewChild} from '@angular/core';
import {NavController, IonicPage, ToastController} from 'ionic-angular';
import {ModalController, PopoverController, AlertController, LoadingController, ViewController, Navbar} from 'ionic-angular';
import {EstabelecimentoProvider} from '../../providers/rest/estabelecimento';

@IonicPage()
@Component({
    selector: 'page-carrinho',
    templateUrl: 'carrinho.html'
})
export class CarrinhoPage {
    Carrinho: any[] = [];
    public settings: any = {};
    subTotal: any;
    prmSubTotal: any;
    GrandTotal: any;
    couponDiscount: any = 0;
    deductedPrice: number = 0;
    otherTaxes = 0.00;
    noOfItems: any;
    total: any;
    coupons: any = [];

    @ViewChild('navbar') navBar: Navbar;

    constructor(public navCtrl: NavController,
                public modalCtrl: ModalController,
                public popoverCtrl: PopoverController,
                public alertCtrl: AlertController,
                public viewCtrl: ViewController,
                public loadingCtrl: LoadingController,
                private estabelecimentoProvider: EstabelecimentoProvider,
                public toastCtrl: ToastController) {

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {
          this.Carrinho = JSON.parse(localStorage.getItem('Carrinho'));

          if (this.Carrinho != null) {
              this.noOfItems = this.Carrinho.length;
              this.callFunction();
          }
        })

        loader.dismiss();

    }

    callFunction() {
      let subTotal = 0;

      if (this.Carrinho != null) {
        for (var i = 0; i <= this.Carrinho.length - 1; i++) {
          //LISTA ADICIONAIS
          let valorAdicional = 0;
          for (var j = 0; j <= this.Carrinho[i].item.listaAdicional.length - 1; j++) {
            valorAdicional = valorAdicional + Number(this.Carrinho[i].item.listaAdicional[j].valor);
          }

          subTotal = subTotal + ((this.Carrinho[i].item.valorItem + valorAdicional) * this.Carrinho[i].item.qtProduto);
        }


        this.subTotal = this.formataMoeda(Number(subTotal));
        this.prmSubTotal = Number(subTotal.toFixed(2));
      }
    }

    deleteItem(idProduto) {
        for (var i = 0; i <= this.Carrinho.length - 1; i++) {
            if (this.Carrinho[i].item.idProduto == idProduto) {
                this.Carrinho.splice(i, 1);
                this.callFunction();
                if (this.Carrinho.length == 0) {
                    localStorage.removeItem('Carrinho');
                    this.noOfItems = null;
                } else {
                    localStorage.setItem('Carrinho', JSON.stringify(this.Carrinho));
                    this.Carrinho = JSON.parse(localStorage.getItem('Carrinho'));
                    this.noOfItems = this.noOfItems - 1;
                }
            }
        }
    }

    nav() {
         if (localStorage.getItem('uid') == null) {
             let alert = this.alertCtrl.create({
                title: 'Desculpe!',
                subTitle: 'Você deve realizar o Login primeiro!',
                buttons: [
                    {
                        text: 'Ok',
                        handler: data => {
                            this.navCtrl.push("LoginPage");
                        }
                    }
                ]
            });

            alert.present();
        }
        else {
          let loader = this.loadingCtrl.create({
              content: "Aguarde...",
          });

          loader.present().then(() => {
            let idEstabelecimento = this.Carrinho[0].item.idEstabelecimento;
            this.estabelecimentoProvider.getStatusEstabelecimento(idEstabelecimento)
              .then((data: any) => {
                if(data.dsStatus == "A") {
                  loader.dismiss();
                  this.navCtrl.push("PedidoPage", {subTotal: this.prmSubTotal});
                } else {
                  let alert = this.alertCtrl.create({
                     title: 'Desculpe!',
                     subTitle: 'O estabelecimento encontra-se fechado no momento.',
                     buttons: [
                         {
                             text: 'Ok',
                             handler: data => {}
                         }
                     ]
                 });
                 loader.dismiss();
                 alert.present();
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
        }
    }

    add(data) {
        if (data.item.qtProduto < 20) {
            data.item.qtProduto = parseInt(data.item.qtProduto) + 1;
            for (let i = 0; i <= this.Carrinho.length - 1; i++) {

                if (this.Carrinho[i].item.idProduto == data.item.idProduto) {
                    this.Carrinho[i].item.qtProduto = data.item.qtProduto;
                }
            }
            localStorage.setItem('Carrinho', JSON.stringify(this.Carrinho));
            this.callFunction();
        }
    }

    remove(data) {
        if (data.item.qtProduto > 1) {
            data.item.qtProduto = data.item.qtProduto - 1;
            for (let i = 0; i <= this.Carrinho.length - 1; i++) {

                if (this.Carrinho[i].item.idProduto == data.item.idProduto) {
                    this.Carrinho[i].item.qtProduto = data.item.qtProduto;
                }
            }
            localStorage.setItem('Carrinho', JSON.stringify(this.Carrinho));
            this.callFunction();
        }
    }


    isCarrinho(): boolean {
        return localStorage.getItem('Carrinho') == null || this.Carrinho.length == 0 ? false : true;
    }

    formataMoeda(numero) {
      var numero = numero.toFixed(2).split('.');
    	numero[0] = "" + numero[0].split(/(?=(?:...)*$)/).join('.');
    	return numero.join(',');
    }

    ionViewDidLoad() {
       this.viewCtrl.setBackButtonText('');
    }
}
