import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {applicationModules} from './materials';
import {AUTH_BASE_PATH} from '../../projects/bhuwanupadhyay/login/src/public-api';

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        BrowserAnimationsModule,
        applicationModules()
    ],
    providers: [
        {
            provide: AUTH_BASE_PATH,
            useValue: 'http://localhost:8080/api/v1'
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
