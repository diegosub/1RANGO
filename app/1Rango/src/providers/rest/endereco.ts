import { Injectable } from '@angular/core';
import { Http, URLSearchParams, Headers, RequestOptions } from '@angular/http';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ConfigProvider } from '../config/config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class EnderecoProvider {

  constructor(public http: Http,
              public config: ConfigProvider) {}

  data: any;

  cadastrar(obj: any) {

    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'EnderecoWS/cadastrar/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/json');
      let options = new RequestOptions({ headers: headers })

      let params = JSON.stringify(obj);

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

  getEnderecos(idUsuario) {
    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'EnderecoWS/getEnderecos/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();
      params.append('idUsuario', idUsuario);

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

  deletarEndereco(idEndereco) {
    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'EnderecoWS/deletarEndereco/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();
      params.append('idEndereco', idEndereco);

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
