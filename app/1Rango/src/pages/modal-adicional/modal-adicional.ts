import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController, AlertController, LoadingController, App  } from 'ionic-angular';
import {AdicionalProvider} from '../../providers/rest/adicional';
import {CarrinhoService} from '../../pages/carrinho.service';

/**
 * Generated class for the ModalAdicionalPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-modal-adicional',
  templateUrl: 'modal-adicional.html',
})
export class ModalAdicionalPage {

  noOfItems: any;
  public dsProduto: any;
  public dsAdicional: any;
  public idProduto: any;
  public Produto: any;
  public vlrProduto: any;
  public valorItem: any;
  public idEstabelecimento: any;
  public dsEstabelecimento: any;

  public SegmentosAdicionais: Array<any> = [];

  public qtdProduto: any;
  public observacao: any;
  count: any = 1;


  public carrinho = {
      idEstabelecimento: Number,
      dsEstabelecimento: String,
      idProduto: Number,
      dsProduto: String,
      vlProduto: Number,
      qtProduto: this.qtdProduto,
      dsObservacao: String,
      valorItem: Number,
      listaSegmentoAdicional: [],
      listaAdicional: []
  };


  constructor(public navCtrl: NavController,
              public loadingCtrl: LoadingController,
              private view: ViewController,
              private alertCtrl: AlertController,
              public carrinhoService: CarrinhoService,
              public appCtrl: App,
              private adicionalProvider: AdicionalProvider,
              public navParams: NavParams) {

          let loader = this.loadingCtrl.create({
              content: "Aguarde...",
          });

          loader.present().then(() => {
            this.idProduto = this.navParams.get('idProduto');
            this.idEstabelecimento = this.navParams.get('idEstabelecimento');
            this.dsEstabelecimento = this.navParams.get('dsEstabelecimento');

            this.adicionalProvider.getAdicional(this.idProduto)
              .then((data: any) => {
                this.Produto = data;

                this.dsProduto = this.Produto.dsProduto;
                this.dsAdicional = this.Produto.dsAdicional;
                this.vlrProduto = this.Produto.dsVlrProduto;
                this.valorItem = this.Produto.vlProduto;
                this.qtdProduto = "1";

                this.SegmentosAdicionais = [];

                if(data.listaSegmentoAdicional != null) {
                  for (var i = 0; i < data.listaSegmentoAdicional.length; i++) {
                    var obj = data.listaSegmentoAdicional[i];
                    this.SegmentosAdicionais.push(obj);
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

  ionViewWillEnter(){
    let carrinho: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));
    this.noOfItems=carrinho!=null ? carrinho.length : null;
  }

  adicionarCarrinho() {

    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });

    loader.present().then(() => {
      this.carrinho.idProduto = this.idProduto;
      this.carrinho.dsProduto = this.dsProduto;
      this.carrinho.vlProduto = this.vlrProduto;
      this.carrinho.qtProduto = this.qtdProduto;
      this.carrinho.dsObservacao = this.observacao;
      this.carrinho.valorItem = this.valorItem;
      this.carrinho.idEstabelecimento = this.idEstabelecimento;
      this.carrinho.dsEstabelecimento = this.dsEstabelecimento;

      this.carrinho.listaSegmentoAdicional = this.SegmentosAdicionais;

      //VALIDAR CAMPOS OBRIGATORIOS
      let flagPreenchido = 'N';

      if(this.carrinho.listaSegmentoAdicional.length == 0) {
        flagPreenchido = 'S'
      }

      for (var i = 0; i <= this.carrinho.listaSegmentoAdicional.length - 1; i++) {
        if(this.carrinho.listaSegmentoAdicional[i].fgObrigatorio == 'S')
        {
          if(this.carrinho.listaSegmentoAdicional[i].fgMultiplosItens == 'N'
                && this.carrinho.listaSegmentoAdicional[i].dsSelecionado != null)
          {
            flagPreenchido = 'S';
          }

          if(this.carrinho.listaSegmentoAdicional[i].fgMultiplosItens == 'S')
          {
            for (var j = 0; j <= this.carrinho.listaSegmentoAdicional[i].listaSegmentoItem.length - 1; j++)
            {
              if(this.carrinho.listaSegmentoAdicional[i].listaSegmentoItem[j].fgSelecionada == true)
              {
                flagPreenchido = 'S';
              }
            }
          }
        }
      }

      if(flagPreenchido == 'N')
      {
        let alert = this.alertCtrl.create({
          title: "Atenção!",
          subTitle: "Todos os adicionais com * devem ser selecionados.",
          buttons: ["Ok"]
        });

        alert.present();
      }
      else
      {
        this.carrinhoService.OnsaveLS(this.carrinho);
        this.view.dismiss();
        this.appCtrl.getRootNav().push("CarrinhoPage");
      }
    })

    loader.dismiss();
  }

  closeModal() {
    this.view.dismiss();
  }

}
