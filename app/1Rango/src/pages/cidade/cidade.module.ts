import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { CidadePage } from './cidade';
import { PipesModule } from '../../app/pipes.module';

@NgModule({
  declarations: [
    CidadePage
  ],
  imports: [
    IonicPageModule.forChild(CidadePage),
    PipesModule
  ],
  exports: [
    CidadePage
  ]
})
export class CidadePageModule {}
