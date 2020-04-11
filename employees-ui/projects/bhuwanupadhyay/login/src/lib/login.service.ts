import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {Router} from '@angular/router';
import {
    AlreadyExistsResponse,
    AUTH_BASE_PATH,
    LoginRequest,
    LoginResponse,
    SignUpRequest
} from './model';
import {Optional} from '@bhuwanupadhyay/utils';

@Injectable({
    providedIn: 'root'
})
export class LoginService {

    public isAuthenticated = new BehaviorSubject<boolean>(false);

    constructor(
        @Inject(AUTH_BASE_PATH) private basePath: string,
        private http: HttpClient,
        private router: Router) {
    }

    async checkAuthenticated() {
        const authenticated = await this.http.get<AlreadyExistsResponse>(`${this.basePath}/iam/already-exists`).toPromise();
        this.isAuthenticated.next(authenticated.exists);
        return Optional.of(authenticated).map(v => v.exists).orElseGet(() => false);
    }

    async login(username: string, password: string) {

        const request: LoginRequest = {
            username: username,
            password: password
        };

        const response = await this.http.post<LoginResponse>(`${this.basePath}/iam/login`, request).toPromise();

        if (response.status !== 'SUCCESS') {
            throw Error('We cannot handle the ' + response.status + ' status');
        }

        this.isAuthenticated.next(true);
    }

    async logout(redirect: string) {
        try {
            await this.http.get<LoginResponse>(`${this.basePath}/iam/logout`).toPromise()
            this.isAuthenticated.next(false);
            await this.router.navigate([redirect]);
        } catch (err) {
            console.error(err);
        }
    }

    async signUp(name: string, password: string, email: string) {
        try {

            const request: SignUpRequest = {
                name: name,
                password: password,
                email: email
            };

            await this.http.post<LoginResponse>(`${this.basePath}/iam/sign-up`, request).toPromise()
            this.isAuthenticated.next(false);
            await this.router.navigate(['../login']);
        } catch (err) {
            console.error(err);
        }
    }


}
