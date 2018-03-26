import { Component, ViewChild } from '@angular/core';
import { IonicPage, NavController, NavParams, ModalController, LoadingController, AlertController, ViewController, Slides } from 'ionic-angular';
import { PedidoProvider } from '../../providers/rest/pedido'

@IonicPage()
@Component({
  selector: 'page-meus-pedidos',
  templateUrl: 'meus-pedidos.html',
})
export class MeusPedidosPage {

  @ViewChild('slider') slider: Slides;

  noOfItems: any;
  idUsuario: any;
  public Pedidos: Array<any> = [];
  public Pendentes: Array<any> = [];

  page = "0";

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public loadingCtrl: LoadingController,
              private view: ViewController,
              private pedidoProvider: PedidoProvider,
              private alertCtrl: AlertController,
              public modalCtrl: ModalController) {

          this.pesquisar();
  }

  selectedTab(ind) {
    this.slider.slideTo(ind);
  }

  ionViewWillEnter(){
    let carrinho: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));
    this.noOfItems=carrinho!=null ? carrinho.length : null;
  }

  pesquisar() {
    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });


    loader.present().then(() => {
      this.idUsuario = Number(localStorage.getItem('uid'));

      //GET ALL PEDIDOS
      this.pedidoProvider.getPedidos(this.idUsuario)
        .then((data: any) => {

          this.Pedidos = [];

          for (var i = 0; i < data.length; i++) {
            var obj = data[i];
            this.Pedidos.push(obj);
          }

          //GET AVALIACOES Pendentes
          this.pedidoProvider.getAvaliacaoPendente(this.idUsuario)
            .then((data: any) => {

              this.Pendentes = [];

              for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                this.Pendentes.push(obj);
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

  navcart() {
    this.navCtrl.push("CarrinhoPage");
  }

  ionViewDidLoad() {
     this.view.setBackButtonText('');
  }

  abrirAvaliacao(idPedido, idEstabelecimento) {
    let modal = this.modalCtrl.create("ModalAvaliacaoPage", {idPedido: idPedido, idEstabelecimento: idEstabelecimento});

    modal.onDidDismiss(data => {
        this.pesquisar();
    });

    modal.present();
  }

  visualizarPedido(idPedido) {
    let modal = this.modalCtrl.create("ModalVisualizarPedidoPage", {idPedido: idPedido});
    modal.present();
  }

}
