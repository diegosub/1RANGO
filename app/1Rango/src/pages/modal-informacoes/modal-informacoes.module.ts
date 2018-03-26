import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ModalInformacoesPage } from './modal-informacoes';

@NgModule({
  declarations: [
    ModalInformacoesPage,
  ],
  imports: [
    IonicPageModule.forChild(ModalInformacoesPage),
  ],
})
export class ModalInformacoesPageModule {}
