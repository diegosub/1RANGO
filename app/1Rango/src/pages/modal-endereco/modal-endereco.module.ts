import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ModalEnderecoPage } from './modal-endereco';

@NgModule({
  declarations: [
    ModalEnderecoPage,
  ],
  imports: [
    IonicPageModule.forChild(ModalEnderecoPage),
  ],
})
export class ModalEnderecoPageModule {}
