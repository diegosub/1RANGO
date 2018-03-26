import { Injectable } from '@angular/core';
import { Http, URLSearchParams, Headers, RequestOptions } from '@angular/http';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ConfigProvider } from '../config/config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class UsuarioProvider {

  constructor(public http: Http,
              public config: ConfigProvider) {}

  data: any;

  cadastrar(obj: any) {

    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'UsuarioWS/cadastrar/';

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

  atualizar(obj: any) {

    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'UsuarioWS/atualizar/';

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

  alterarSenha(obj: any) {

    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'UsuarioWS/alterarSenha/';

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

  recuperarSenha(dsEmail) {

    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'UsuarioWS/recuperarSenha/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();
      params.append('dsEmail', dsEmail);

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

  getUsuario(idUsuario) {

    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'UsuarioWS/getUsuario/';

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

  login(dsEmail, dsSenha) {

    return new Promise((resolve, reject) => {

      let url = this.config.apiUrl + 'UsuarioWS/login/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();
      params.append('dsEmail', dsEmail);
      params.append('dsSenha', dsSenha);

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
