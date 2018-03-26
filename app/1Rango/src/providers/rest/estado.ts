import { Injectable } from '@angular/core';
import { Http, URLSearchParams, Headers, RequestOptions } from '@angular/http';
import { Response } from '@angular/http';
import { ConfigProvider } from '../config/config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class EstadoProvider {

  constructor(public http: Http,
              public config: ConfigProvider ) {}

  data: any;

  getEstados() {

    return new Promise((resolve, reject) => {
      let url = this.config.apiUrl + 'EstadoWS/getEstados/';

      let headers = new Headers();
      headers.append('accept', 'application/json');
      headers.append('Content-type', 'application/x-www-form-urlencoded');
      let options = new RequestOptions({ headers: headers })

      let params = new URLSearchParams();

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
