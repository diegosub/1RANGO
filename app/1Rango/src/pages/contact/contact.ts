import {Component} from '@angular/core';
import {IonicPage, NavController, NavParams, LoadingController, AlertController, ViewController} from 'ionic-angular';
import {NgForm} from "@angular/forms";
import {EmailComposer} from '@ionic-native/email-composer';
import {ContatoProvider} from '../../providers/rest/contato';

@IonicPage()
@Component({
    selector: 'page-contact',
    templateUrl: 'contact.html',
    providers: [EmailComposer]
})
export class ContactPage {
    noOfItems: any;
    user: any = {};
    Contato: any;

    constructor(public navCtrl: NavController,
                private contatoProvider: ContatoProvider,
                public navParams: NavParams,
                private alertCtrl: AlertController,
                private view: ViewController,
                public loadingCtrl: LoadingController,
                public emailComposer: EmailComposer) {

    }

    ionViewWillEnter(){
      let cart: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));
      this.noOfItems=cart!=null ? cart.length : null;
    }

    onSend(user: NgForm) {

      let contato = {dsNome: String, dsEmail: String, dsMensagem: String}

      contato.dsNome = this.user.name;
      contato.dsEmail = this.user.email;
      contato.dsMensagem = this.user.message;

      let loader = this.loadingCtrl.create({
          content: "Aguarde...",
      });

      loader.present().then(() => {
        this.contatoProvider.cadastrar(contato)
          .then((data: any) => {
            this.Contato = data;

            if(this.Contato.mensagem != null
                && this.Contato.mensagem != '')
            {
              let msg = this.alertCtrl.create({
                title: "Atenção!",
                subTitle: this.Contato.mensagem,
                buttons: ["Ok"]
              });

              msg.present();
            }
            else
            {
              let msg = this.alertCtrl.create({
                title: "Sucesso!",
                subTitle: "Sua mensagem foi enviada.",
                buttons: ["Ok"]
              });

              msg.present();
              this.navCtrl.push("HomePage");
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

    navcart() {
      this.navCtrl.push("CarrinhoPage");
    }

    ionViewDidLoad() {
       this.view.setBackButtonText('');
    }

}
