import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ModalRecuperarSenhaPage } from './modal-recuperar-senha';

@NgModule({
  declarations: [
    ModalRecuperarSenhaPage,
  ],
  imports: [
    IonicPageModule.forChild(ModalRecuperarSenhaPage),
  ],
})
export class ModalRecuperarSenhaPageModule {}
