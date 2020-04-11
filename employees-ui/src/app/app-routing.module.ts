import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGardGuard, LoginModule} from 'projects/bhuwanupadhyay/login/src/public-api';
import {HomeModule} from '../../projects/bhuwanupadhyay/home/src/public-api';


const routes: Routes = [
    {
        path: '',
        loadChildren: () => HomeModule,
        canActivate: [AuthGardGuard]
    },
    {
        path: 'iam',
        loadChildren: () => LoginModule
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
