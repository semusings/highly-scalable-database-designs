import {Injectable} from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    Router,
    RouterStateSnapshot
} from '@angular/router';
import {LoginService} from './login.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGardGuard implements CanActivate {

    constructor(private loginService: LoginService,
                private router: Router) {
    }

    async canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Promise<boolean> {
        if (!await this.loginService.checkAuthenticated()) {
            await this.router.navigate(['/iam']);
            return false;
        }
        return true;
    }

}
