import { Injectable } from '@angular/core';
import { Http, URLSearchParams, Headers, RequestOptions } from '@angular/http';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ConfigProvider } from '../config/config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class EstabelecimentoProvider {

  constructor(public http: Http,
              public config: ConfigProvider) {}

  data: any;

  getEstabelecimentos(idCidade) {
    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'EstabelecimentoWS/getEstabelecimentos/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();
      params.append('idCidade', idCidade);

      this.http.post(url, params, options)
        .map(result => result.json())
        .subscribe(data => {
          this.data = data;
          resolve(this.data);
        },
        (error) => {
          reject(error);
        });
    });
  }

  getInfoEstabelecimento(idEstabelecimento) {
    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'EstabelecimentoWS/getInfoEstabelecimento/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();
      params.append('idEstabelecimento', idEstabelecimento);

      this.http.post(url, params, options)
        .map(result => result.json())
        .subscribe(data => {
          this.data = data;
          resolve(this.data);
        },
        (error) => {
          reject(error);
        });
    });
  }

  getStatusEstabelecimento(idEstabelecimento) {
    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'EstabelecimentoWS/getStatusEstabelecimento/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();
      params.append('idEstabelecimento', idEstabelecimento);

      this.http.post(url, params, options)
        .map(result => result.json())
        .subscribe(data => {
          this.data = data;
          resolve(this.data);
        },
        (error) => {
          reject(error);
        });
    });
  }

}
