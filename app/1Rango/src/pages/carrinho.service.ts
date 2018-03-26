import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {map} from 'rxjs/Operator/map'


@Injectable()


export class CarrinhoService {

    Carrinho: any[] = [];
    itemCarrinho: any = {};
    itemInCarrinho = [];
    listaAdicional = [];

    public adicional = {
      descricao: String,
      dsValor: String,
      valor: Number
    }

    constructor() {
        this.Carrinho = JSON.parse(localStorage.getItem('Carrinho'));
    }

    OnsaveLS(item: any){

        this.Carrinho = JSON.parse(localStorage.getItem('Carrinho'));
        let ExtotalPrice : number = 0;
        let totalPrice :number = 0;
        this.itemInCarrinho = [];
        this.listaAdicional = [];

        //CONFIGURANDO LISTA DE ADICIONAIS
        for (var i = 0; i <= item.listaSegmentoAdicional.length - 1; i++) {

          let adicionalSingle = {descricao: String, dsValor: String, valor: 0};
          let descricao: any = "";
          let dsValor: any = "";
          let valor = 0;

          if(item.listaSegmentoAdicional[i].dsSelecionado != null) {
            descricao = item.listaSegmentoAdicional[i].dsSelecionado.split("¬", 3)[0];
            dsValor = item.listaSegmentoAdicional[i].dsSelecionado.split("¬", 3)[1];
            valor = item.listaSegmentoAdicional[i].dsSelecionado.split("¬", 3)[2];

            adicionalSingle.descricao = descricao;
            adicionalSingle.dsValor = dsValor;
            adicionalSingle.valor = valor;

            this.listaAdicional.push(adicionalSingle);
          }

          for(var z = 0; z <= item.listaSegmentoAdicional[i].listaSegmentoItem.length - 1; z++){
              if(item.listaSegmentoAdicional[i].listaSegmentoItem[z].fgSelecionada == true) {

                let adicionalMultiple = {descricao: String, dsValor: String, valor: 0};

                descricao = item.listaSegmentoAdicional[i].listaSegmentoItem[z].dsSegmentoItem;
                dsValor = item.listaSegmentoAdicional[i].listaSegmentoItem[z].strVlAdicional;
                valor = item.listaSegmentoAdicional[i].listaSegmentoItem[z].vlAdicional;

                adicionalMultiple.descricao = descricao;
                adicionalMultiple.dsValor = dsValor;
                adicionalMultiple.valor = valor;

                this.listaAdicional.push(adicionalMultiple);
              }
          }
        }

        item.listaAdicional = this.listaAdicional;

        if (this.Carrinho == null){
            this.itemCarrinho.item = item;
            this.itemInCarrinho.push(this.itemCarrinho);
            localStorage.setItem('Carrinho', JSON.stringify(this.itemInCarrinho));
        }
        else
        {
            this.itemCarrinho.item = item;
            this.Carrinho.push(this.itemCarrinho);
            localStorage.setItem('Carrinho', JSON.stringify(this.Carrinho));
        }
    }
}
