import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ConfigProvider } from '../config/config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';


@Injectable()
export class CepProvider {

  constructor(public http: Http,
              public config: ConfigProvider ) {}

  data: any;

  getCep(cep) {

    return new Promise((resolve, reject) => {
      let url = "http://viacep.com.br/ws/"+cep+"/json/";

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
}
