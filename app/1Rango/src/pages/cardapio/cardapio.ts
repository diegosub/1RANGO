import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, AlertController, LoadingController, ModalController, ViewController} from 'ionic-angular';
import {CardapioProvider} from '../../providers/rest/cardapio';
import {FavoritoProvider} from '../../providers/rest/favorito';

@IonicPage()
@Component({
  selector: 'page-cardapio',
  templateUrl: 'cardapio.html',
})
export class CardapioPage {

  noOfItems: any;
  public Segmentos: Array<any> = [];
  public idEstabelecimento: any;

  public Cardapio: any;
  public dsEstabelecimento: String;
  public dsCategoria: String;
  public dsIcone: String;
  public dsStatus: String;
  public dsImagem: String;
  public dsClassificacao: String;
  public dsDelivery: String;
  public dsRetirada: String;
  public dsVlMinimo: String;
  public dsTxEntrega: String;
  public dsFavorito: any;

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public loadingCtrl: LoadingController,
              private view: ViewController,
              public modalCtrl: ModalController,
              private alertCtrl: AlertController,
              private favoritoProvider: FavoritoProvider,
              private cardapioProvider: CardapioProvider) {

          this.getCardapio();
          this.setarFavorito();

  }

  setarFavorito() {
    let idEstabelecimento = this.navParams.get('idEstabelecimento');
    let idUsuario = Number(localStorage.getItem('uid'));

    this.favoritoProvider.getFavorito(idUsuario, idEstabelecimento)
      .then((data: any) => {
        this.dsFavorito = data.dsFavorito;
      })
      .catch((error: any) => {
        let alert = this.alertCtrl.create({
          title: "Ops...",
          subTitle: "Não foi possível estabelecer uma conexão com o servidor.",
          buttons: ["Ok"]
        });

        alert.present();
        this.navCtrl.push("HomePage");
      });
  }

  getCardapio() {
    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });


    loader.present().then(() => {
      this.idEstabelecimento = this.navParams.get('idEstabelecimento');
      this.cardapioProvider.getCardapio(this.idEstabelecimento)
        .then((data: any) => {
          this.Cardapio = data;
          this.dsEstabelecimento = data.estabelecimento.dsFantasia;
          this.dsCategoria = data.estabelecimento.dsCategoria;
          this.dsIcone = data.estabelecimento.dsIcone;
          this.dsStatus = data.estabelecimento.dsStatus;
          this.dsImagem = data.estabelecimento.dsImagem;
          this.dsClassificacao = data.estabelecimento.dsClassificacao;
          this.dsDelivery = data.estabelecimento.dsDelivery;
          this.dsRetirada = data.estabelecimento.dsRetirada;
          this.dsVlMinimo = data.estabelecimento.dsVlMinimo;
          this.dsTxEntrega = data.estabelecimento.dsEntrega;

          this.Segmentos = [];

          if(data.listaSegmento != null) {
            for (var i = 0; i < data.listaSegmento.length; i++) {
              var obj = data.listaSegmento[i];
              this.Segmentos.push(obj);
            }
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

  info(idEstabelecimento) {
    let modal = this.modalCtrl.create("ModalInformacoesPage", {idEstabelecimento: idEstabelecimento});
    modal.present();
  }

  visualizarAvaliacoes(idEstabelecimento) {
    let modal = this.modalCtrl.create("VisualizarAvaliacaoPage", {idEstabelecimento: idEstabelecimento});
    modal.present();
  }

  abrirModalBairros(idEstabelecimento) {
    let modal = this.modalCtrl.create("ModalBairroPage", {idEstabelecimento: idEstabelecimento});
    modal.present();
  }

  adicional(idProduto, idEstabelecimento, dsEstabelecimento) {
    if(this.dsStatus == 'Fechado') {
      let alert = this.alertCtrl.create({
        title: "Atenção!",
        subTitle: "Este estabelecimento encontra-se fechado.",
        buttons: ["Ok"]
      });

      alert.present();
    }
    else {
      //this.navCtrl.push("AdicionalPage", {idProduto: idProduto, idEstabelecimento: idEstabelecimento, dsEstabelecimento: dsEstabelecimento});
      let modal = this.modalCtrl.create("ModalAdicionalPage",  {idProduto: idProduto, idEstabelecimento: idEstabelecimento, dsEstabelecimento: dsEstabelecimento});
      modal.present();
    }
  }

  favoritar() {
    let favorito = {idFavorito: Number, idEstabelecimento: Number, idUsuario: 0}
    favorito.idEstabelecimento = this.idEstabelecimento;
    favorito.idUsuario = Number(localStorage.getItem('uid'));

    this.favoritoProvider.cadastrar(favorito)
      .then((data: any) => {
        if(data.mensagem != null
            && data.mensagem != '')
        {
          let msg = this.alertCtrl.create({
            title: "Atenção!",
            subTitle: data.mensagem,
            buttons: ["Ok"]
          });

          msg.present();
        }
        else
        {
          this.getCardapio();
          this.setarFavorito();
        }

      })
      .catch((error: any) => {
        let alert = this.alertCtrl.create({
          title: "Ops...",
          subTitle: "Não foi possível estabelecer uma conexão com o servidor.",
          buttons: ["Ok"]
        });

        alert.present();
        this.navCtrl.setRoot("HomePage");
      });
  }

  desfavoritar() {
    let favorito = {idFavorito: Number, idEstabelecimento: Number, idUsuario: 0}
    favorito.idEstabelecimento = this.idEstabelecimento;
    favorito.idUsuario = Number(localStorage.getItem('uid'));

    this.favoritoProvider.remover(favorito)
      .then((data: any) => {
        if(data.mensagem != null
            && data.mensagem != '')
        {
          let msg = this.alertCtrl.create({
            title: "Atenção!",
            subTitle: data.mensagem,
            buttons: ["Ok"]
          });

          msg.present();
        }
        else
        {
          this.getCardapio();
          this.setarFavorito();
        }

      })
      .catch((error: any) => {
        let alert = this.alertCtrl.create({
          title: "Ops...",
          subTitle: "Não foi possível estabelecer uma conexão com o servidor.",
          buttons: ["Ok"]
        });

        alert.present();
        this.navCtrl.setRoot("HomePage");
      });
  }

  navcart() {
    this.navCtrl.push("CarrinhoPage");
  }

  ionViewDidLoad() {
     this.view.setBackButtonText('');
  }

}
