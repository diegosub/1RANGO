import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';

/**
 * Generated class for the PedidoFinalizadoPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-pedido-finalizado',
  templateUrl: 'pedido-finalizado.html',
})
export class PedidoFinalizadoPage {

  idPedido: any;
  dsEstabelecimento: any;

  constructor(public navCtrl: NavController,
              public navParams: NavParams) {

        this.idPedido = this.navParams.get('idPedido');
        this.dsEstabelecimento = this.navParams.get('dsEstabelecimento');

  }

  inicio() {
    this.navCtrl.setRoot("HomePage");
  }

  meusPedidos() {
    this.navCtrl.setRoot("MeusPedidosPage");
  }

}
