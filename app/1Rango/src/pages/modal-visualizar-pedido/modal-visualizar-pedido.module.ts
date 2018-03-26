import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ModalVisualizarPedidoPage } from './modal-visualizar-pedido';

@NgModule({
  declarations: [
    ModalVisualizarPedidoPage,
  ],
  imports: [
    IonicPageModule.forChild(ModalVisualizarPedidoPage),
  ],
})
export class ModalVisualizarPedidoPageModule {}
