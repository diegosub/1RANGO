import {Component, OnInit} from '@angular/core';
import {IonicPage, NavController, LoadingController, Platform, AlertController,Events} from 'ionic-angular';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {UsuarioProvider} from '../../providers/rest/usuario'

@IonicPage()
@Component({
    selector: 'page-registration',
    templateUrl: 'registration.html'
})
export class RegistrationPage implements OnInit {

    registration: FormGroup;

    Usuario: any;

    public objeto = {
      dsNome: String,
      dsEmail: String,
      nmTelefone: String,
      dsSenha: String
    }

    constructor(public navCtrl: NavController,
                public events:Events,
                public loadingCtrl: LoadingController,
                public alertCtrl: AlertController,
                public usuarioProvider: UsuarioProvider,
                public platform: Platform) {

    }

    onSubmit() {

      let loader = this.loadingCtrl.create({
          content: "Aguarde...",
      });

      this.objeto.dsNome = this.registration.value.name;
      this.objeto.dsEmail = this.registration.value.email;
      this.objeto.nmTelefone = this.registration.value.telefone;
      this.objeto.dsSenha = this.registration.value.password;

      loader.present().then(() => {
        this.usuarioProvider.cadastrar(this.objeto)
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
              localStorage.setItem('uid', this.Usuario.idUsuario);
              localStorage.setItem('unm', this.Usuario.dsNome);
              this.navCtrl.setRoot("HomePage");
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

        // this.af.auth.createUserWithEmailAndPassword(this.registration.value.email, this.registration.value.password)
        //     .then((success) => {
        //         this.db.object('/users/' + success.uid).update({
        //             name: this.registration.value.name,
        //             email: this.registration.value.email,
        //             mobileNo: this.registration.value.mobileNo,
        //             role: 'User'
        //         });
        //
        //         localStorage.setItem('uid', success.uid);
        //         this.navCtrl.setRoot("HomePage");
        //     })
        //     .catch((error) => {
        //         console.log("Firebase failure: " + JSON.stringify(error));
        //         this.showAlert(error.message);
        //     });

    }

    tel_mask(v) {
      var v = this.registration.value.telefone;

      v=v.replace(/\D/g,"")
      v=v.replace(/^(\d{2})(\d)/g,"($1) $2");
      v=v.replace(/(\d)(\d{4})$/,"$1-$2");

      this.registration = new FormGroup({
        'name': new FormControl(this.registration.value.name, Validators.required),
        'email': new FormControl(this.registration.value.email, [Validators.required, Validators.pattern("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$")]),
        'password': new FormControl(this.registration.value.pessword, [Validators.required, Validators.minLength(4), Validators.maxLength(24)]),
        'telefone': new FormControl(v, Validators.required)
      });
    }

    showAlert(message) {
        let alert = this.alertCtrl.create({
            subTitle: message,
            buttons: ['OK']
        });
        alert.present();
    }

    navLogin() {
        this.navCtrl.setRoot("LoginPage");
    }

    ngOnInit(): any {
        this.buildForm();
    }

    //Validation
    buildForm(): void {
        this.registration = new FormGroup({
            'name': new FormControl('', Validators.required),
            'telefone': new FormControl('', Validators.required),
            'email': new FormControl('', [Validators.required, Validators.pattern("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$")]),
            'password': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(24)])
        });
    }



}
