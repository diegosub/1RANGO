import { Injectable } from '@angular/core';
import { Http, URLSearchParams, Headers, RequestOptions } from '@angular/http';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ConfigProvider } from '../config/config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';


@Injectable()
export class AdicionalProvider {

  constructor(public http: Http,
              public config: ConfigProvider) {}

  data: any;

  getAdicional(idProduto) {
    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'AdicionalWS/getAdicional/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();
      params.append('idProduto', idProduto);

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
