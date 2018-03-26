import {Component, ViewChild} from '@angular/core';
import {IonicPage, NavController, NavParams, ViewController} from 'ionic-angular';
import {Nav, Platform} from 'ionic-angular';
import {Slides} from 'ionic-angular';
import {CallNumber} from '@ionic-native/call-number';
import {EmailComposer} from '@ionic-native/email-composer';

@IonicPage()

@Component({
    selector: 'page-about-us',
    templateUrl: 'about-us.html',
    providers: [CallNumber, EmailComposer]
})

export class AboutUsPage {

    noOfItems: any;
    @ViewChild(Slides) slides: Slides;
    @ViewChild(Nav) nav: Nav;


    constructor(public platform: Platform,
                public navCtrl: NavController,
                public callNumber: CallNumber,
                public navParams: NavParams,
                private view: ViewController,
                public emailComposer: EmailComposer) {
    }

    ionViewWillEnter(){
      let cart: Array<any> = JSON.parse(localStorage.getItem('Carrinho'));
      this.noOfItems=cart!=null ? cart.length : null;
    }

    goToSlide() {
        this.slides.slideTo(2, 500);
    }

    navcart() {
      this.navCtrl.push("CarrinhoPage");
    }

    ionViewDidLoad() {
       this.view.setBackButtonText('');
    }
}
