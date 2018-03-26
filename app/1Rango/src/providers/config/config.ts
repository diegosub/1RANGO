import { Injectable } from '@angular/core';

@Injectable()
export class ConfigProvider {

  //desenvolvimento
  public apiUrl = '/1rango/rango-ws/';

  //rede interna
  //public apiUrl = 'http://192.168.15.12:8080/rango-ws/';

  //rede exterma
  //public apiUrl = 'http://191.34.186.199:8080/rango-ws/';

  constructor() {}

}
