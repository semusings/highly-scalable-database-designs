import {NgModule} from '@angular/core';
import {LoginComponent} from './login.component';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {CommonModule} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {SignupComponent} from './signup/signup.component';
import {MatIconModule} from '@angular/material/icon';
import {ExtendedModule, FlexModule} from '@angular/flex-layout';
import {ResetPasswordComponent} from './reset-password/reset-password.component';
import {LoginRoutingModule} from './login-routing.module';
import {ServerErrorTranslatorPipe} from './server-error-translator.pipe';

@NgModule({
    declarations: [
        LoginComponent,
        SignupComponent,
        ResetPasswordComponent,
        ServerErrorTranslatorPipe
    ],
    imports: [
        MatCardModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatInputModule,
        CommonModule,
        MatButtonModule,
        MatIconModule,
        FlexModule,
        ExtendedModule,
        LoginRoutingModule
    ],
    exports: [
        LoginComponent,
        SignupComponent,
        ResetPasswordComponent
    ]
})
export class LoginModule {
}
