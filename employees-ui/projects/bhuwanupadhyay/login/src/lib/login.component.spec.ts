import {LoginComponent} from './login.component';
import {LoginService} from './login.service';

describe('LoginComponent', () => {
    let component: LoginComponent;
    let service: LoginService;

    beforeEach(() => {
        service = jest.fn() as unknown as LoginService;
        component = new LoginComponent(
            null,
            null,
            null,
            service
        );
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

});
