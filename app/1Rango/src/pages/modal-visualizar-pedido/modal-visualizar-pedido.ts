import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController, AlertController, LoadingController } from 'ionic-angular';
import { PedidoProvider } from '../../providers/rest/pedido';

@IonicPage()
@Component({
  selector: 'page-modal-visualizar-pedido',
  templateUrl: 'modal-visualizar-pedido.html',
})
export class ModalVisualizarPedidoPage {

  Pedido: any;
  public PedidoProdutos: Array<any> = [];
  idPedido: any;
  dsEstabelecimento: any;
  strDataPedido: any;
  strDataRecebimento: any;
  strDataCancelamento: any;
  strDataConclusao: any;
  dsMotivoCancelamento: any;
  idAvaliacao: any;
  vlAvaliacao: any;
  dsAvaliacao: any;
  idTipoPedido: any;
  idFormaPagamento: any;
  dsValorGeral: any;
  dsTroco: any;
  dsObservacao: any;
  dsCartao: any;
  dsEndereco: any;
  dsRua: any;
  dsNumero: any;
  dsBairro: any;
  dsCidade: any;
  siglaEstado: any;
  dsCep: any;
  idEndereco: any;


  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public loadingCtrl: LoadingController,
              private view: ViewController,
              private pedidoProvider: PedidoProvider,
              private alertCtrl: AlertController) {

        this.idPedido = this.navParams.get('idPedido');

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {
          this.pedidoProvider.visualizarPedido(this.idPedido)
            .then((data: any) => {
              this.Pedido = data;

              this.dsEstabelecimento = this.Pedido.dsEstabelecimento;
              this.strDataPedido = this.Pedido.strDtCadastro;
              this.strDataRecebimento = this.Pedido.strDtRecebimento;
              this.strDataCancelamento = this.Pedido.strDtCancelamento;
              this.strDataConclusao = this.Pedido.strDataConclusao;
              this.dsMotivoCancelamento = this.Pedido.dsMotivoCancelamento;
              this.PedidoProdutos = this.Pedido.listaPedidoProduto;
              this.idAvaliacao = this.Pedido.idAvaliacao;
              this.idTipoPedido = this.Pedido.idTipoPedido;
              this.idFormaPagamento = this.Pedido.idFormaPagamento;
              this.dsValorGeral = this.Pedido.dsValorGeral;
              this.dsTroco = this.Pedido.dsTroco;
              this.dsObservacao = this.Pedido.dsObservacao;
              this.dsCartao = this.Pedido.dsCartao;
              this.idEndereco = this.Pedido.idEndereco;

              this.dsEndereco = this.Pedido.dsEndereco;
              this.dsRua = this.Pedido.dsRua;
              this.dsNumero = this.Pedido.dsNumero;
              this.dsBairro = this.Pedido.dsBairro;
              this.dsCidade = this.Pedido.dsCidade;
              this.siglaEstado = this.Pedido.siglaEstado;
              this.dsCep = this.Pedido.dsCep;

              this.vlAvaliacao = this.Pedido.vlAvaliacao;
              this.dsAvaliacao = this.Pedido.dsAvaliacao;

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
        });
  }

  closeModal() {
    this.view.dismiss();
  }

}
