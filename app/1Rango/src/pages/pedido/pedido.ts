import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, LoadingController, ModalController, AlertController, ViewController } from 'ionic-angular';
import {EstabelecimentoProvider} from '../../providers/rest/estabelecimento';
import {EnderecoProvider} from '../../providers/rest/endereco';
import {CartaoProvider} from '../../providers/rest/cartao';
import {PedidoProvider} from '../../providers/rest/pedido';


@IonicPage()
@Component({
  selector: 'page-pedido',
  templateUrl: 'pedido.html',
})
export class PedidoPage {

  Carrinho: any[] = [];
  Pedido: any;
  public Estabelecimentos: Array<any> = [];
  public Enderecos: Array<any> = [];
  public Bairros: Array<any> = [];
  public CartoesDelivery: Array<any> = [];
  public CartoesRetirada: Array<any> = [];
  Estabelecimento: any;
  dsEstabelecimento: any;
  idEstabelecimento: any;
  idUsuario: any;
  fgDelivery: any;
  fgRetirada: any;
  fgDinheiroDelivery: any;
  fgCartaoDelivery: any;
  fgDinheiroRetirada: any;
  fgCartaoRetirada: any;
  tipoPedido: any;
  formaPagamento: any;
  idCartao: any;
  dsBairroTaxa: any;
  idBairroTaxa: any;
  strValorTaxa: any;
  vlTaxa: any;
  idEndereco: any;
  subTotal: any;
  strSubTotal: any;
  total: any;
  strTotal: any;
  strVlPagamento: any;
  observacao: any;

  public pedido = {
    idPedido: Number,
    idEstabelecimento: Number,
    idUsuario: Number,
    idStatus: Number,
    idTipoPedido: Number,
    idFormaPagamento: Number,
    idCartao: Number,
    idEndereco: Number,
    idBairro: Number,
    vlTotalPedido: Number,
    vlPagamento: Number,
    vlTaxaEntrega: Number,
    dsObservacao: String,
    mensagem: String,

    listaPedidoProduto: []
  }

  constructor(public navCtrl: NavController,
              public loadingCtrl: LoadingController,
              public modalCtrl: ModalController,
              private estabelecimentoProvider: EstabelecimentoProvider,
              private enderecoProvider: EnderecoProvider,
              private pedidoProvider: PedidoProvider,
              private view: ViewController,
              private cartaoProvider: CartaoProvider,
              private alertCtrl: AlertController,
              public navParams: NavParams) {

        this.Carrinho = JSON.parse(localStorage.getItem('Carrinho'));
        this.dsEstabelecimento = this.Carrinho[0].item.dsEstabelecimento;
        this.idEstabelecimento = this.Carrinho[0].item.idEstabelecimento;
        this.idUsuario = Number(localStorage.getItem('uid'));
        this.dsBairroTaxa = "Selecione o Bairro";
        this.idBairroTaxa = 0;
        this.idEndereco = 0;
        this.subTotal = this.navParams.get('subTotal');
        this.strSubTotal = this.formataMoeda(Number(this.subTotal));
        this.vlTaxa = 0;
        this.idCartao = 0;
        this.total = Number(this.subTotal);
        this.strTotal = this.formataMoeda(this.total);

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {

          this.getEnderecos(this.idUsuario, loader);
          this.getCartoesDelivery(this.idEstabelecimento, loader);
          this.getCartoesRetirada(this.idEstabelecimento, loader);

          this.estabelecimentoProvider.getInfoEstabelecimento(this.idEstabelecimento)
            .then((data: any) => {
              this.Estabelecimento = data;

              this.fgDelivery = this.Estabelecimento.fgDelivery;
              this.fgRetirada = this.Estabelecimento.fgRetiradaLocal;
              this.fgDinheiroDelivery = this.Estabelecimento.fgDinheiroDelivery;
              this.fgCartaoDelivery = this.Estabelecimento.fgCartaoDelivery;
              this.fgDinheiroRetirada = this.Estabelecimento.fgDinheiroRetirada;
              this.fgCartaoRetirada = this.Estabelecimento.fgCartaoRetirada;

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

  getEnderecos(idUsuario, loader) {
    this.enderecoProvider.getEnderecos(this.idUsuario)
      .then((data: any) => {

        this.Enderecos = [];

        for (var i = 0; i < data.length; i++) {
          var obj = data[i];
          this.Enderecos.push(obj);
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
  }

  getCartoesDelivery(idEstabelecimento, loader) {
    this.cartaoProvider.getCartoesDelivery(this.idEstabelecimento)
      .then((data: any) => {

        this.CartoesDelivery = [];

        for (var i = 0; i < data.length; i++) {
          var obj = data[i];
          this.CartoesDelivery.push(obj);
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
  }

  getCartoesRetirada(idEstabelecimento, loader) {
    this.cartaoProvider.getCartoesRetirada(this.idEstabelecimento)
      .then((data: any) => {

        this.CartoesRetirada = [];

        for (var i = 0; i < data.length; i++) {
          var obj = data[i];
          this.CartoesRetirada.push(obj);
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
  }

  selecionarEndereco(idEndereco) {
      this.idEndereco = idEndereco;
  }

  changeTipo() {
    this.idEndereco = 0.
    this.dsBairroTaxa = "Selecione o Bairro";
    this.idBairroTaxa = 0;
    this.strValorTaxa = 0;
    this.formaPagamento = 0;
    this.total = Number(this.subTotal);
    this.strTotal = this.formataMoeda(this.total);
    this.idCartao = 0;
    this.strVlPagamento = null;
  }

  changeFormaPagamento() {
    this.strVlPagamento = null;
    this.idCartao = 0;
  }

  abrirModalBairros(idEstabelecimento) {
    let modal = this.modalCtrl.create("ModalBairroPage", {idEstabelecimento: idEstabelecimento});
    modal.onDidDismiss(data => {
        if(data != null) {
          this.idBairroTaxa = data.idBairro;
          this.dsBairroTaxa = data.dsBairro;
          this.strValorTaxa = data.strValorTaxa;
          this.vlTaxa = data.vlTaxa;

          this.total = this.subTotal + this.vlTaxa;
          this.strTotal = this.formataMoeda(this.total);
        }
    });
    modal.present();
  }

  abrirModalNovoEndereco() {
    let modal = this.modalCtrl.create("ModalEnderecoPage");

    modal.onDidDismiss(data => {
        this.getEnderecos(this.idUsuario, null);
    });

    modal.present();
  }

  concluir() {
    let mensagem = this.validar();

    if(mensagem != "") {
      let alert = this.alertCtrl.create({
        title: "Atenção",
        subTitle: mensagem,
        buttons: ["Ok"]
      });

      alert.present();
    } else {
      //SUCESSO
      this.popularObjetoPedido();
      this.enviarPedido();
    }
  }

  validar() {
    let mensagem = "";

    if(this.tipoPedido == null || this.tipoPedido == 0) {
      mensagem = "Selecione o tipo do pedido (Delivery ou Retirada).";
      return mensagem;
    }

    if(this.tipoPedido == 1) {
      if(this.idEndereco == null || this.idEndereco == 0) {
        mensagem = "Selecione um endereço de entrega.";
        return mensagem;
      }

      if(this.idBairroTaxa == null || this.idBairroTaxa == 0) {
        mensagem = "Selecione o bairro de entrega (Taxa).";
        return mensagem;
      }
    }

    if(this.formaPagamento == null || this.formaPagamento == 0) {
      mensagem = "Selecione a forma de pagamento (Dinheiro ou Cartão).";
      return mensagem;
    }

    if(this.strVlPagamento != null && this.strVlPagamento != '') {
      if(Number(this.strVlPagamento.replace(".", "").replace(",", ".")) <= (Number(this.subTotal) + Number(this.vlTaxa))) {
        mensagem = "O valor para troco deve ser maior que o valor total do pedido.";
        return mensagem;
      }
    }


    if(this.formaPagamento == 2 && (this.idCartao == null || this.idCartao == 0)) {
      mensagem = "Selecione o seu cartão para realizar o pagamento.";
      return mensagem;
    }

    return mensagem;
  }

  popularObjetoPedido() {
    this.pedido.idEstabelecimento = this.idEstabelecimento;
    this.pedido.idUsuario = this.idUsuario;
    this.pedido.idTipoPedido = this.tipoPedido;
    this.pedido.idFormaPagamento = this.formaPagamento;
    this.pedido.idCartao = this.idCartao;
    this.pedido.idEndereco = this.idEndereco;
    this.pedido.idBairro = this.idBairroTaxa;
    this.pedido.vlTotalPedido = this.subTotal;

    if(this.strVlPagamento != null && this.strVlPagamento != '') {
      this.pedido.vlPagamento = this.strVlPagamento.replace(".", "").replace(",", ".");
    }

    this.pedido.vlTaxaEntrega = this.vlTaxa;
    this.pedido.dsObservacao = this.observacao;

    //LISTA DE PRODUTOS
    for (var i = 0; i <= this.Carrinho.length - 1; i++) {
      let pedidoProduto = {
         idPedidoProduto: Number, idPedido: Number, idProduto: Number, dsProduto: String, qtProduto: Number, vlProduto: Number, dsObservacao: String,
         listaPedidoProdutoAdicional: []
      }

      pedidoProduto.idProduto    = this.Carrinho[i].item.idProduto;
      pedidoProduto.dsProduto    = this.Carrinho[i].item.dsProduto;
      pedidoProduto.qtProduto    = this.Carrinho[i].item.qtProduto;
      pedidoProduto.vlProduto    = this.Carrinho[i].item.valorItem;
      pedidoProduto.dsObservacao = this.Carrinho[i].item.dsObservacao;

      //this.pedido.listaProduto[i] = pedidoProduto;

      for (var j = 0; j <= this.Carrinho[i].item.listaAdicional.length - 1; j++) {
        let pedidoProdutoAdicional = {idPedidoProdutoAdicional: Number, idPedidoProduto: Number, dsAdicional: String, vlAdicional: Number}

        pedidoProdutoAdicional.dsAdicional = this.Carrinho[i].item.listaAdicional[j].descricao;
        pedidoProdutoAdicional.vlAdicional = this.Carrinho[i].item.listaAdicional[j].valor;

        pedidoProduto.listaPedidoProdutoAdicional[j] = pedidoProdutoAdicional;
      }

      this.pedido.listaPedidoProduto[i] = pedidoProduto;
    }
  }

  enviarPedido() {
    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });

    loader.present().then(() => {
      this.pedidoProvider.cadastrar(this.pedido)
        .then((data: any) => {
          this.Pedido = data;

          if(this.Pedido.mensagem != null
              && this.Pedido.mensagem != '')
          {
            let msg = this.alertCtrl.create({
              title: "Atenção!",
              subTitle: this.Pedido.mensagem,
              buttons: ["Ok"]
            });

            msg.present();
          } else {
            this.limparCarrinho();
            this.navCtrl.setRoot("PedidoFinalizadoPage", {idPedido: this.Pedido.idPedido, dsEstabelecimento: this.dsEstabelecimento});
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
          this.navCtrl.setRoot("HomePage");
        });
    })

  }

  limparCarrinho() {
    let num = this.Carrinho.length-1;

    for (var i = 0; i <= num; i++) {
      localStorage.removeItem('Carrinho');
    }
  }

  formataMoeda(numero) {
    var numero = numero.toFixed(2).split('.');
    numero[0] = "" + numero[0].split(/(?=(?:...)*$)/).join('.');
    return numero.join(',');
  }

  formataCompoDecimal()
  {
      let campo = this.strVlPagamento;

  	  if(campo != null && campo != '')
  	  {
  		  let decs=2;

  		  let money = campo;

  		  if(parseFloat(money.replace(',','.')) < 0){ money = ''; }

  		   while(money.toString().indexOf('.') != -1)
  		   {
  		   	money = money.replace(".","");
  		   }
  		   money = money.replace(",",".");
  		   if (isNaN(Number(money)))
  		   {
  			   campo = '';
           this.strVlPagamento = campo;
  			   return;
  		   }
  		   money = new Number(money);
  		   money = money.toFixed(decs);
  		   money = money.toString().replace(".",",");
  		   var number, comma, numberAux = "";

  		   number = ( money + "" ).replace( "\.", "" );

  		   var zeros = "";
  		   for (var i=0; i<decs; i++) zeros +="0";

  		   number = ( number.indexOf( "," ) == -1 ? number + "," + zeros : number ); //se for numero inteiro, coloca-se as casas decimais
  		   comma = number.indexOf( "," ) -1; //posicao da virgula

  		   for ( var x = comma, count = 1; x >= 0; x--, count++ )
  		   {
  			   numberAux = number.substring( x, x +1 ) + numberAux;
  			   if ( count == (3) ) {
  				   if ( x != 0 && number.substring( x -1, x ) != "-" ) {
  				       numberAux = "." + numberAux;
  				       count = 0;
  				   }
  			   }
  		   }

  		   var comma_casas_decimais = number.substring( comma +1, number.length );
  		   if (comma_casas_decimais.length == decs)
  		   {
  		   		comma_casas_decimais += '0';
  		   }

  		   campo = numberAux + comma_casas_decimais;
         this.strVlPagamento = campo;
  	   }
  }

  ionViewDidLoad() {
     this.view.setBackButtonText('');
  }
}
