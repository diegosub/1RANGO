import {NgModule, ErrorHandler} from '@angular/core';
import {IonicApp, IonicModule, IonicErrorHandler} from 'ionic-angular';
import {MyApp} from './app.component';
import {CarrinhoService} from '../pages/carrinho.service';
import {TranslateModule, TranslateStaticLoader, TranslateLoader} from 'ng2-translate/ng2-translate';
import {Http, HttpModule} from '@angular/http';
import {BrowserModule} from "@angular/platform-browser";

import { SuperTabsModule } from 'ionic2-super-tabs';

import 'firebase/storage';
import { CidadeProvider } from '../providers/rest/cidade';
import { EstabelecimentoProvider } from '../providers/rest/estabelecimento';
import { ConfigProvider } from '../providers/config/config';
import { CardapioProvider } from '../providers/rest/cardapio';
import { AdicionalProvider } from '../providers/rest/adicional';
import { UsuarioProvider } from '../providers/rest/usuario';
import { EstadoProvider } from '../providers/rest/estado';
import { EnderecoProvider } from '../providers/rest/endereco';
import { CepProvider } from '../providers/rest/cep';
import { BairroProvider } from '../providers/rest/bairro';
import { CartaoProvider } from '../providers/rest/cartao';
import { PedidoProvider } from '../providers/rest/pedido';
import { FavoritoProvider } from '../providers/rest/favorito';
import { ContatoProvider } from '../providers/rest/contato';
import { AvaliacaoProvider } from '../providers/rest/avaliacao';


export function createTranslateLoader(http: Http) {
  return new TranslateStaticLoader(http, './assets/i18n', '.json');
}


@NgModule({
  declarations: [
    MyApp
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    BrowserModule,
    HttpModule,
    SuperTabsModule.forRoot(),
    TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: createTranslateLoader,
      deps: [Http]
    })
  ],
  exports: [BrowserModule],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler},
    CarrinhoService,
    CidadeProvider,
    EstabelecimentoProvider,
    ConfigProvider,
    CardapioProvider,
    AdicionalProvider,
    UsuarioProvider,
    EstadoProvider,
    EnderecoProvider,
    CepProvider,
    BairroProvider,
    CartaoProvider,
    PedidoProvider,
    FavoritoProvider,
    ContatoProvider,
    AvaliacaoProvider
  ]
})
export class AppModule {
}
