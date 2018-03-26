import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { FavoritoPage } from './favorito';
import {PipesModule} from '../../app/pipes.module';

@NgModule({
  declarations: [
    FavoritoPage
  ],
  imports: [
    IonicPageModule.forChild(FavoritoPage),
    PipesModule
  ],
  exports: [
    FavoritoPage
  ]
})
export class FavoritoPageModule {}
