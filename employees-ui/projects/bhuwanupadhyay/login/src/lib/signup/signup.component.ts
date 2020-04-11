import {Component, OnInit} from '@angular/core';
import {BasicFormComponent} from '../model';
import {FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginService} from '../login.service';

@Component({
    selector: 'bhuwanupadhyay-signup',
    template: `
    <mat-card>
      <mat-card-content>
        <form [formGroup]="form" (ngSubmit)="onSubmit()">
          <h2>Sign Up</h2>
          <mat-error *ngIf="hasServerErrors">
            <ul>
              <li *ngFor="let err of serverErrors">
                <span [class]="'server-error-' + err.errorId">{{err | serverErrorTranslator }}</span>jj
              </li>
            </ul>
          </mat-error>
          <mat-form-field class="full-width-input">
            <input matInput
                   placeholder="Name"
                   type="text"
                   formControlName="name"
                   required>
            <mat-error>
              Please provide a valid name
            </mat-error>
          </mat-form-field>
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
          <mat-form-field class="full-width-input">
            <input matInput
                   placeholder="Confirm Password"
                   type="password"
                   autocomplete="off"
                   formControlName="confirmPassword"
                   required>
            <mat-error>
              Password does not match
            </mat-error>
          </mat-form-field>
          <div fxLayout="row" fxShow="false" fxShow.gt-sm>
            <button type="submit" mat-button color="primary">
              <mat-icon>power_settings_new</mat-icon>
              Submit
            </button>
            <button mat-button type="button" routerLink="../">
              <mat-icon>exit_to_app</mat-icon>
              Already User
            </button>
          </div>
        </form>
      </mat-card-content>
    </mat-card>
  `,
    styleUrls: ['./signup.component.scss']
})
export class SignupComponent extends BasicFormComponent implements OnInit {

    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private loginService: LoginService
    ) {
        super();
    }

    async ngOnInit() {
        this.form = this.fb.group({
            name: ['', Validators.required],
            email: ['', Validators.email],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required]
        });
    }

    async onSubmit() {

    }

}
