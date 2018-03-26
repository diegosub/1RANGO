import {Component} from '@angular/core';
import {IonicPage, NavController, LoadingController} from 'ionic-angular';


@IonicPage()

@Component({
    selector: 'page-home',
    templateUrl: 'home.html'
})
export class HomePage {

    mySlideOptions = {
        initialSlide: 1,
        loop: true,
        autoplay: 2000,
        pager: false
    };

    Cart: any = [];
    noOfItems: any;

    nomeUsuario: any;


    constructor(public navCtrl: NavController,
                public loadingCtrl: LoadingController) {

        let loader = this.loadingCtrl.create({
            content: "Aguarde...",
        });

        loader.present().then(() => {
            this.nomeUsuario = localStorage.getItem('unm');
        })

        loader.dismiss();

    }

    ionViewWillEnter(){
      this.Cart = JSON.parse(localStorage.getItem('Cart'));
      this.noOfItems=this.Cart!=null ? this.Cart.length : null;
    }

    navigate(id) {
        this.navCtrl.push("ProductListPage", {id: id});
    }

    navcart() {
        this.navCtrl.push("CartPage");
    }

    redirectToLogin() {
        this.navCtrl.push("LoginPage");
    }

    redirectToCidade() {
        this.navCtrl.push("CidadePage");
    }

    isLoggedin() {
      return localStorage.getItem('uid') != null;
    }

}
