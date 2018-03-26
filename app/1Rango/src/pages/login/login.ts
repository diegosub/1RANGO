import {Component} from '@angular/core';
import {IonicPage, NavController, LoadingController, Platform, AlertController,Events, ModalController} from 'ionic-angular';
import {CustomValidators} from 'ng2-validation';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {UsuarioProvider} from '../../providers/rest/usuario'


@IonicPage()
@Component({
    selector: 'page-login',
    templateUrl: 'login.html',
})
export class LoginPage {
    tagHide: boolean = true;
    valForm: FormGroup;

    Usuario: any;

    constructor(public navCtrl: NavController,
                public fb: FormBuilder,
                public loadingCtrl: LoadingController,
                public alertCtrl: AlertController,
                public platform: Platform,
                public usuarioProvider: UsuarioProvider,
                public modalCtrl: ModalController,
                public events:Events) {

        this.valForm = fb.group({
            'email': ['', Validators.compose([Validators.required, CustomValidators.email])],
            'password': ['', Validators.required]
        });

    }

    toggleRegister() {
        this.tagHide = this.tagHide ? false : true;
    }

    OnLogin($ev, value: any) {

        let loader = this.loadingCtrl.create({
          content: "Aguarde...",
        });

        $ev.preventDefault();
        for (let c in this.valForm.controls) {
            this.valForm.controls[c].markAsTouched();
        }

        if (this.valForm.valid) {
            loader.present().then(() => {
              this.usuarioProvider.login(value.email, value.password).then((success) => {

                this.Usuario = success;

                if(this.Usuario != null && this.Usuario.idUsuario != null && this.Usuario.idUsuario > 0) {
                    localStorage.setItem('uid', this.Usuario.idUsuario);
                    localStorage.setItem('unm', this.Usuario.dsNome);
                    this.navCtrl.setRoot("HomePage");

                    loader.dismiss();
                }
                else
                {
                    let alert = this.alertCtrl.create({
                      title: "Atenção!",
                      subTitle: "Este usuário não se encontra na nossa base de dados. Favor verificar E-mail e Senha.",
                      buttons: ["Ok"]
                    });

                    loader.dismiss();
                    alert.present();
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
                this.navCtrl.setRoot("HomePage");
              });
          })
        }
    }

  // private publishEvent(){
  //      this.db.object('/users/' + this.af.auth.currentUser.uid).valueChanges().subscribe(userInfo => {
  //       this.events.publish('imageUrl',userInfo);
  //     });
  //   }

    showAlert(message) {
        let alert = this.alertCtrl.create({
            subTitle: message,
            buttons: ['OK']
        });
        alert.present();
    }

    Register() {
        this.navCtrl.setRoot("RegistrationPage");
    }

    esqueceuSenha() {
      let modal = this.modalCtrl.create("ModalRecuperarSenhaPage");
      modal.present();
    }
}
