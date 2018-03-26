import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ModalAlterarSenhaPage } from './modal-alterar-senha';

@NgModule({
  declarations: [
    ModalAlterarSenhaPage,
  ],
  imports: [
    IonicPageModule.forChild(ModalAlterarSenhaPage),
  ],
})
export class ModalAlterarSenhaPageModule {}
