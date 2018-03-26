import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController, AlertController, LoadingController } from 'ionic-angular';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UsuarioProvider } from '../../providers/rest/usuario';

/**
 * Generated class for the ModalAlterarSenhaPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-modal-recuperar-senha',
  templateUrl: 'modal-recuperar-senha.html',
})
export class ModalRecuperarSenhaPage {

  url: any = 'assets/img/logo1rango.png';
  recuperarSenhaForm: FormGroup;
  Usuario: any;

  public objeto = {
    idUsuario: 0,
    dsEmail: String
  }

  constructor(public navCtrl: NavController,
              public loadingCtrl: LoadingController,
              private usuarioProvider: UsuarioProvider,
              private view: ViewController,
              private alertCtrl: AlertController,
              public navParams: NavParams) {

                this.recuperarSenhaForm = new FormGroup({
                    'email': new FormControl('', [Validators.required, Validators.pattern("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$")])
                });
  }

  onSubmit() {

    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });

    loader.present().then(() => {
      this.usuarioProvider.recuperarSenha(this.recuperarSenhaForm.value.email)
        .then((data: any) => {
          this.Usuario = data;

          if(this.Usuario.mensagem != null
              && this.Usuario.mensagem != '')
          {
            let msg = this.alertCtrl.create({
              title: "Atenção!",
              subTitle: this.Usuario.mensagem,
              buttons: ["Ok"]
            });

            msg.present();
          }
          else
          {
            let msg = this.alertCtrl.create({
              title: "Sucesso!",
              subTitle: "Enviamos o link de recuperação de senha para o seu e-mail.",
              buttons: ["Ok"]
            });

            this.closeModal();
            msg.present();
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
          this.closeModal();
        });
    })
  }

  closeModal() {
    this.view.dismiss();
  }

}
