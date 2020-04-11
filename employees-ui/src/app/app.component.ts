import {Component} from '@angular/core';
import {LoginService} from '../../projects/bhuwanupadhyay/login/src/public-api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Employees Manager';
  isAuthenticated: boolean;

  constructor(private loginService: LoginService) {
    this.loginService.isAuthenticated.subscribe(
      (isAuthenticated: boolean) => this.isAuthenticated = isAuthenticated
    );
  }

  async ngOnInit() {
    this.isAuthenticated = await this.loginService.checkAuthenticated();
  }

  async logout() {
    await this.loginService.logout('/');
  }
}
