import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, LoadingController, ViewController, AlertController, ModalController } from 'ionic-angular';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CustomValidators } from 'ng2-validation';
import { UsuarioProvider } from '../../providers/rest/usuario';

@IonicPage()
@Component({
  selector: 'page-meus-dados',
  templateUrl: 'meus-dados.html',
})
export class MeusDadosPage {

  noOfItems: any;
  dadosForm: FormGroup;
  url: any = 'assets/img/logo1rango.png';
  userName: any;
  idUsuario: any;
  Usuario: any;

  public objeto = {
    idUsuario: 0,
    dsNome: String,
    dsEmail: String,
    nmTelefone: String
  }

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              private view: ViewController,
              public modalCtrl: ModalController,
              public fb: FormBuilder,
              private usuarioProvider: UsuarioProvider,
              private alertCtrl: AlertController,
              public loadingCtrl: LoadingController) {

        this.dadosForm = fb.group({
            'name': ['', Validators.required],
            'email': ['', Validators.compose([Validators.required, CustomValidators.email])],
            'telefone': ['', Validators.required]
        });

        this.userName = localStorage.getItem('unm');
        this.idUsuario = localStorage.getItem('uid');

        this.getUsuario();

  }

  getUsuario() {
    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });


    loader.present().then(() => {
      this.idUsuario = Number(localStorage.getItem('uid'));
      this.usuarioProvider.getUsuario(this.idUsuario)
        .then((data: any) => {

          this.dadosForm = this.fb.group({
            'name': [data.dsNome, Validators.required],
            'email': [data.dsEmail, Validators.compose([Validators.required, CustomValidators.email])],
            'telefone': [data.nmTelefone , Validators.required]
          });

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
    })
  }

  ionViewWillEnter(){
    let carrinho: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));
    this.noOfItems=carrinho!=null ? carrinho.length : null;
  }

  tel_mask(v) {
    var v = this.dadosForm.value.telefone;

    v=v.replace(/\D/g,"")
    v=v.replace(/^(\d{2})(\d)/g,"($1) $2");
    v=v.replace(/(\d)(\d{4})$/,"$1-$2");

    this.dadosForm = this.fb.group({
      'name': [this.dadosForm.value.name, Validators.required],
      'email': [this.dadosForm.value.email, Validators.compose([Validators.required, CustomValidators.email])],
      'telefone': [v , Validators.required]
    });
  }

  ionViewDidLoad() {
     this.view.setBackButtonText('');
  }

  onSubmit() {
    this.preencherObjeto();

    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });

    loader.present().then(() => {
      this.usuarioProvider.atualizar(this.objeto)
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

            localStorage.setItem('unm', this.Usuario.dsNome);

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

  abrirAlterarSenha() {
    let modal = this.modalCtrl.create("ModalAlterarSenhaPage");
    modal.present();
  }

  preencherObjeto() {
    this.objeto.idUsuario = Number(localStorage.getItem('uid'));
    this.objeto.dsNome = this.dadosForm.value.name;
    this.objeto.dsEmail = this.dadosForm.value.email;
    this.objeto.nmTelefone = this.dadosForm.value.telefone;
  }

  navcart() {
    this.navCtrl.push("CarrinhoPage");
  }

}
