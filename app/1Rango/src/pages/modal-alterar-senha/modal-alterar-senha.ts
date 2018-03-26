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
  selector: 'page-modal-alterar-senha',
  templateUrl: 'modal-alterar-senha.html',
})
export class ModalAlterarSenhaPage {

  url: any = 'assets/img/logo1rango.png';
  alterarSenhaForm: FormGroup;
  Usuario: any;

  public objeto = {
    idUsuario: 0,
    dsSenha: String,
    dsNovaSenha: String,
    dsConfirmaNovaSenha: String
  }

  constructor(public navCtrl: NavController,
              public loadingCtrl: LoadingController,
              private usuarioProvider: UsuarioProvider,
              private view: ViewController,
              private alertCtrl: AlertController,
              public navParams: NavParams) {

                this.alterarSenhaForm = new FormGroup({
                    'senha': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(24)]),
                    'novaSenha': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(24)]),
                    'confirmar': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(24)])
                });
  }

  onSubmit() {
    if(this.alterarSenhaForm.value.novaSenha != this.alterarSenhaForm.value.confirmar) {

      let error = this.alertCtrl.create({
        title: "Atenção!",
        subTitle: "O campo Nova senha deve ser igual ao campo Confirmar.",
        buttons: ["Ok"]
      });
      error.present();

    } else {

      this.preencherObjeto();

      let loader = this.loadingCtrl.create({
          content: "Aguarde...",
      });

      loader.present().then(() => {
        this.usuarioProvider.alterarSenha(this.objeto)
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
                subTitle: "Seus dados foram atualizados.",
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
            this.navCtrl.setRoot("HomePage");
          });
      })

    }
  }

  preencherObjeto() {
    this.objeto.idUsuario = Number(localStorage.getItem('uid'));
    this.objeto.dsSenha = this.alterarSenhaForm.value.senha;
    this.objeto.dsNovaSenha = this.alterarSenhaForm.value.novaSenha;
    this.objeto.dsConfirmaNovaSenha = this.alterarSenhaForm.value.confirmar;
  }

  closeModal() {
    this.view.dismiss();
  }

}
