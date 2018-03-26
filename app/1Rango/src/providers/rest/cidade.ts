import { Injectable } from '@angular/core';
import {  Http, URLSearchParams, Headers, RequestOptions } from '@angular/http';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ConfigProvider } from '../config/config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

/*
Generated class for the RestProvider provider.

See https://angular.io/docs/ts/latest/guide/dependency-injection.html
for more info on providers and Angular DI.
*/
@Injectable()
export class CidadeProvider {

  constructor(public http: Http,
              public config: ConfigProvider ) {}

  data: any;

  getAll() {

    return new Promise((resolve, reject) => {
      let url = this.config.apiUrl + 'CidadeWS/getAll/';

      this.http.post(url, null, null)
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

  getCidadeBairro(idEstabelecimento) {
    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'CidadeWS/getCidadeBairro/';

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
