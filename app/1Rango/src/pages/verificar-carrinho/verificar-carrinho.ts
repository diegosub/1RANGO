import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController } from 'ionic-angular';

/**
 * Generated class for the ModalCarrinhoPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-verificar-carrinho',
  templateUrl: 'verificar-carrinho.html',
})
export class VerificarCarrinhoPage {
  Carrinho: any[] = [];
  dsEstabelecimento: any;

  constructor(public navCtrl: NavController, public navParams: NavParams, private view: ViewController) {
    this.Carrinho = JSON.parse(localStorage.getItem('Carrinho'));

    if (this.Carrinho != null) {
        this.dsEstabelecimento = this.Carrinho[0].item.dsEstabelecimento;
    }

  }

  navcart() {
    this.navCtrl.push("CarrinhoPage");
  }

  cancelarCarrinho() {
    let num = this.Carrinho.length-1;

    for (var i = 0; i <= num; i++) {
      localStorage.removeItem('Carrinho');
    }

    this.navCtrl.pop();
  }

  ionViewDidLoad() {
     this.view.setBackButtonText('');
  }

}
