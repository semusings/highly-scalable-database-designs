import {Component, OnInit} from '@angular/core';
import {LoginService} from './login.service';
import {FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {BasicFormComponent} from './model';
import { Optional } from '@bhuwanupadhyay/utils';

@Component({
  selector: 'bhuwanupadhyay-login',
  template: `
    <mat-card>
      <mat-card-content>
        <form [formGroup]="form" (ngSubmit)="onSubmit()">
          <h2>Log In</h2>
          <mat-error *ngIf="loginInvalid">
            The username and password were not recognised
          </mat-error>
          <mat-form-field class="full-width-input">
            <input matInput
                   placeholder="Email"
                   autocomplete="email"
                   type="email"
                   formControlName="email"
                   required>
            <mat-error>
              Please provide a valid email
            </mat-error>
          </mat-form-field>
          <mat-form-field class="full-width-input">
            <input matInput
                   placeholder="Password"
                   type="password"
                   autocomplete="off"
                   formControlName="password"
                   required>
            <mat-error>
              Please provide a valid password
            </mat-error>
          </mat-form-field>
          <div fxLayout="row" fxShow="false" fxShow.gt-sm>
            <button type="submit" mat-button color="primary">
              <mat-icon>power_settings_new</mat-icon>
              Login
            </button>
            <button mat-button type="button" routerLink="reset-password">
              <mat-icon>exit_to_app</mat-icon>
              Reset Password
            </button>
            <button mat-button type="button" color="accent" routerLink="sign-up">
              <mat-icon>gamepad</mat-icon>
              Sign Up
            </button>
          </div>
        </form>
      </mat-card-content>
    </mat-card>
  `,
  styleUrls: ['./login.component.scss']
})
export class LoginComponent extends BasicFormComponent implements OnInit {

  public loginInvalid: boolean;
  private formSubmitAttempt: boolean;
  private returnUrl: string;

  constructor(
    protected fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService
  ) {
    super();
  }

  async ngOnInit() {
    this.returnUrl = Optional
      .of(this.route.snapshot.queryParams.returnUrl)
      .orElse('/game');

    this.form = this.fb.group({
      email: ['', Validators.email],
      password: ['', Validators.required]
    });

    if (await this.loginService.checkAuthenticated()) {
      await this.router.navigate([this.returnUrl]);
    }
  }

  async onSubmit() {
    this.loginInvalid = false;
    this.formSubmitAttempt = false;
    if (this.form.valid) {
      try {
        const username = this.form.get('username').value;
        const password = this.form.get('password').value;
        await this.loginService.login(username, password);
      } catch (err) {
        this.loginInvalid = true;
      }
    } else {
      this.formSubmitAttempt = true;
    }
  }


}
