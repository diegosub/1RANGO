import {Component, ViewChild} from '@angular/core';
import {Nav, Platform, Events} from 'ionic-angular';
import {StatusBar} from '@ionic-native/status-bar';
import {SplashScreen} from '@ionic-native/splash-screen';

import {OneSignal} from '@ionic-native/onesignal';
import {SocialSharing} from '@ionic-native/social-sharing';

@Component({
  templateUrl: 'app.html',
  selector: 'MyApp',
  providers: [StatusBar, SplashScreen, OneSignal, SocialSharing]
})

export class MyApp {
   @ViewChild(Nav) nav: Nav;
  Cart: any = [];
  noOfItemsInCart: any;
  userID: any;
  name: any;
  imageUrl:any='assets/img/logo1rango.png';
  rootPage: string = "HomePage";
  mask: any[] = ['+', '1', ' ', '(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];

  constructor(  public platform: Platform,
                public statusbar: StatusBar,
                public splashscreen: SplashScreen,
                public socialSharing: SocialSharing,
                private oneSignal: OneSignal,
                private events: Events) {

      this.initializeApp();
  }

  ngOnInit(){
    this.userID = localStorage.getItem('uid');
    if (this.userID != null) {
      this.name = localStorage.getItem('unm');
      this.imageUrl= 'assets/img/logo1rango.png';
    }

    this.Cart = JSON.parse(localStorage.getItem('Carrinho'));
    this.noOfItemsInCart=this.Cart != null ? this.Cart.length : null;
  }


  initializeApp() {
    if (this.platform.ready()) {
      this.platform.ready().then((res) => {
        if (res == 'cordova') {
          this.oneSignal.startInit('9740a50f-587f-4853-821f-58252d998399', '714618018341');
          this.oneSignal.inFocusDisplaying(this.oneSignal.OSInFocusDisplayOption.InAppAlert);
          this.oneSignal.handleNotificationReceived().subscribe(() => {
          });
          this.oneSignal.handleNotificationOpened().subscribe(() => {
          });
          this.oneSignal.endInit();
        }
      });
    }
  }

  home() {
    this.nav.setRoot("HomePage");
  }

  dado() {
    this.nav.push("MeusDadosPage");
  }

  meusPedidos() {
    this.nav.push("MeusPedidosPage");
  }

  addToCart() {
    this.nav.push("CarrinhoPage");
  }

  endereco() {
    this.nav.push("EnderecoPage");
  }

  catagory() {
    this.nav.push("CategoryPage");
  }

  favorito() {
    this.nav.push("FavoritoPage");
  }

  contact() {
    this.nav.push("ContactPage");
  }

  aboutUs() {
    this.nav.push("AboutUsPage");
  }

  login() {
    this.nav.setRoot("LoginPage");
  }

  logout() {
    localStorage.removeItem('uid');
    localStorage.removeItem('unm');
    this.imageUrl='assets/img/logo1rango.png';
    this.nav.setRoot("HomePage");
  }

  isLoggedin() {
    return localStorage.getItem('uid') != null;
  }

  isCart(){
     this.name = localStorage.getItem('unm');
     this.Cart = JSON.parse(localStorage.getItem('Carrinho'));
     this.noOfItemsInCart=this.Cart != null ? this.Cart.length : null;
     return true;
  }

}
