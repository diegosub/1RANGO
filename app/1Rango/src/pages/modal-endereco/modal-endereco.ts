import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ViewController, LoadingController, AlertController } from 'ionic-angular';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {EstadoProvider} from '../../providers/rest/estado';
import {CepProvider} from '../../providers/rest/cep';
import {EnderecoProvider} from '../../providers/rest/endereco';

/**
 * Generated class for the ModalEnderecoPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-modal-endereco',
  templateUrl: 'modal-endereco.html',
})
export class ModalEnderecoPage {

  enderecoForm: FormGroup;
  public Estados: Array<any> = [];
  Endereco: any;

  public objeto = {
	    idUsuario: 0,
	    dsEndereco: String,
	    dsCep: String,
    	dsRua: String,
    	dsNumero: String,
    	dsComplemento: String,
    	dsBairro: String,
    	dsCidade: String,
    	dsReferencia: String,
    	dtCadastro: String,
      siglaEstado: String,
    	mensagem: String
  }

  constructor(public navCtrl: NavController,
              public navParams: NavParams,
              public loadingCtrl: LoadingController,
              public fb: FormBuilder,
              private alertCtrl: AlertController,
              private estadoProvider: EstadoProvider,
              private enderecoProvider: EnderecoProvider,
              private cepProvider: CepProvider,
              private view: ViewController) {

        this.enderecoForm = fb.group({
            'descricao': ['', Validators.required],
            'cep': ['', Validators.required],
            'rua': ['', Validators.required],
            'numero': ['', Validators.required],
            'bairro': ['', Validators.required],
            'cidade': ['', Validators.required],
            'uf': ['', Validators.required],
            'complemento': ['', !Validators.required],
            'referencia': ['', !Validators.required]
        });

        //LISTA DE ESTADO
        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {
          this.estadoProvider.getEstados()
            .then((data: any) => {
              this.Estados = [];
              loader.dismiss();

              for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                this.Estados.push(obj);
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
            });
        })

  }

  onSubmit() {
    this.preencherObjeto();

    let loader = this.loadingCtrl.create({
        content: "Aguarde...",
    });

    loader.present().then(() => {
      this.enderecoProvider.cadastrar(this.objeto)
        .then((data: any) => {
          this.Endereco = data;

          if(this.Endereco.mensagem != null
              && this.Endereco.mensagem != '')
          {
            let msg = this.alertCtrl.create({
              title: "Atenção!",
              subTitle: this.Endereco.mensagem,
              buttons: ["Ok"]
            });

            msg.present();
          }
          else
          {
            let msg = this.alertCtrl.create({
              title: "Sucesso!",
              subTitle: "Endereço cadastrado.",
              buttons: ["Ok"]
            });

            msg.present();
          }
          this.closeModal();
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

  cep_mask() {
     var v = this.enderecoForm.value.cep;

     v=v.replace(/\D/g,"")
     v=v.replace(/^(\d{5})(\d)/,"$1-$2")

     this.enderecoForm = this.fb.group({
       'descricao': [this.enderecoForm.value.descricao, Validators.required],
       'cep': [v , Validators.required],
       'rua': [this.enderecoForm.value.rua, Validators.required],
       'numero': [this.enderecoForm.value.numero, Validators.required],
       'bairro': [this.enderecoForm.value.bairro, Validators.required],
       'cidade': [this.enderecoForm.value.cidade, Validators.required],
       'uf': [this.enderecoForm.value.uf, Validators.required],
       'complemento': [this.enderecoForm.value.complemento, !Validators.required],
       'referencia': [this.enderecoForm.value.referencia, !Validators.required]
     });
 }

 onCep() {
   if(this.enderecoForm.value.cep.length < 9) {
     this.enderecoForm = this.fb.group({
       'descricao': [this.enderecoForm.value.descricao, Validators.required],
       'cep': ['' , Validators.required],
       'rua': [this.enderecoForm.value.rua, Validators.required],
       'numero': [this.enderecoForm.value.numero, Validators.required],
       'bairro': [this.enderecoForm.value.bairro, Validators.required],
       'cidade': [this.enderecoForm.value.cidade, Validators.required],
       'uf': [this.enderecoForm.value.uf, Validators.required],
       'complemento': [this.enderecoForm.value.complemento, !Validators.required],
       'referencia': [this.enderecoForm.value.referencia, !Validators.required]
     });
   }
   else
   {
     this.buscaCep();
   }
 }

 preencherObjeto() {
   this.objeto.idUsuario        = Number(localStorage.getItem('uid'));
   this.objeto.dsEndereco       = this.enderecoForm.value.descricao;
   this.objeto.dsCep            = this.enderecoForm.value.cep;
   this.objeto.dsRua            = this.enderecoForm.value.rua;
   this.objeto.dsNumero         = this.enderecoForm.value.numero;
   this.objeto.dsComplemento    = this.enderecoForm.value.complemento;
   this.objeto.dsBairro         = this.enderecoForm.value.bairro;
   this.objeto.dsCidade         = this.enderecoForm.value.cidade;
   this.objeto.siglaEstado      = this.enderecoForm.value.uf;
   this.objeto.dsReferencia     = this.enderecoForm.value.referencia;
 }

 buscaCep() {
   let loader = this.loadingCtrl.create({
       content: "Aguarde...",
   });

   loader.present().then(() => {
     this.cepProvider.getCep(this.enderecoForm.value.cep)
       .then((data: any) => {

         this.enderecoForm = this.fb.group({
           'descricao': [this.enderecoForm.value.descricao, Validators.required],
           'cep': [data.cep , Validators.required],
           'rua': [data.logradouro, Validators.required],
           'numero': [this.enderecoForm.value.numero, Validators.required],
           'bairro': [data.bairro, Validators.required],
           'cidade': [data.localidade, Validators.required],
           'uf': [data.uf, Validators.required],
           'complemento': [this.enderecoForm.value.complemento, !Validators.required],
           'referencia': [this.enderecoForm.value.referencia, !Validators.required]
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
       });
   })
 }

 closeModal() {
   this.view.dismiss();
 }

}
