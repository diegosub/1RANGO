import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PedidoFinalizadoPage } from './pedido-finalizado';

@NgModule({
  declarations: [
    PedidoFinalizadoPage,
  ],
  imports: [
    IonicPageModule.forChild(PedidoFinalizadoPage),
  ],
})
export class PedidoFinalizadoPageModule {}
