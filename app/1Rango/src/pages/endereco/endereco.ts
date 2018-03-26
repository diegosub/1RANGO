import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ModalController, LoadingController, AlertController, ViewController } from 'ionic-angular';
import { EnderecoProvider } from '../../providers/rest/endereco'

@IonicPage()
@Component({
  selector: 'page-endereco',
  templateUrl: 'endereco.html',
})
export class EnderecoPage {

  noOfItems: any;
  idUsuario: any;
  public Enderecos: Array<any> = [];

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public loadingCtrl: LoadingController,
              private enderecoProvider: EnderecoProvider,
              private view: ViewController,
              private alertCtrl: AlertController,
              public modalCtrl: ModalController) {

          this.pesquisar();
  }

  ionViewWillEnter(){
    let cart: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));
    this.noOfItems=cart!=null ? cart.length : null;
  }

  pesquisar() {
    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });


    loader.present().then(() => {
      this.idUsuario = Number(localStorage.getItem('uid'));
      this.enderecoProvider.getEnderecos(this.idUsuario)
        .then((data: any) => {

          this.Enderecos = [];

          for (var i = 0; i < data.length; i++) {
            var obj = data[i];
            this.Enderecos.push(obj);
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

  abrirModal() {
    let modal = this.modalCtrl.create("ModalEnderecoPage");

    modal.onDidDismiss(data => {
        this.pesquisar();
    });

    modal.present();
  }

  deletarEndereco(idEndereco) {

    let alert = this.alertCtrl.create({
      title: 'Excluir',
      message: 'Deseja realmente excluir este endereço?',
      buttons: [
      {
        text: 'Sim',
        role: 'sim',
        handler: () => {
          this.delete(idEndereco);
        }
      },
      {
        text: 'Não',
        handler: () => {

        }
      }]
    });

    alert.present();

  }

  delete(idEndereco) {
    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });


    loader.present().then(() => {
      this.enderecoProvider.deletarEndereco(idEndereco)
        .then((data: any) => {
          this.pesquisar();
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
        });
    })
  }

  ionViewDidLoad() {
     this.view.setBackButtonText('');
  }

  navcart() {
    this.navCtrl.push("CarrinhoPage");
  }

}
