import {Component} from '@angular/core';
import {IonicPage, NavController, NavParams, AlertController, LoadingController, ModalController } from 'ionic-angular';
import {EstabelecimentoProvider} from '../../providers/rest/estabelecimento'

@IonicPage()
@Component({
  selector: 'page-estabelecimento',
  templateUrl: 'estabelecimento.html',
})
export class EstabelecimentoPage {

  noOfItems: any;

  public cidade: String;
  public idCidade: any;
  public Estabelecimentos: Array<any> = [];
  public selecionados: Array<any> = [];

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public modalCtrl: ModalController,
              public loadingCtrl: LoadingController,
              private alertCtrl: AlertController,
              private estabelecimentoProvider: EstabelecimentoProvider) {

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {
          this.idCidade = this.navParams.get('idCidade');
          this.estabelecimentoProvider.getEstabelecimentos(this.idCidade)
            .then((data: any) => {
              this.Estabelecimentos = [];

              for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                this.cidade = obj.dsCidade;
                this.Estabelecimentos.push(obj);
                this.selecionados = this.Estabelecimentos;
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

  navcart() {
    this.navCtrl.push("CarrinhoPage");
  }

  filtrar(ev: any) {
    this.inicializarLista();
    let val = ev.target.value;
    if (val && val.trim() != '') {
        this.Estabelecimentos = this.Estabelecimentos.filter((data) => {
            return (data.dsEstabelecimento.toLowerCase().indexOf(val.toLowerCase()) > -1);
        })
    }
  }

  inicializarLista() {
    this.Estabelecimentos = this.selecionados;
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

}
