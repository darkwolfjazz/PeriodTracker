import { Routes } from '@angular/router';
import { Auth } from './pages/auth/auth';
import { Dashboard } from './pages/dashboard/dashboard';
import { authGuard } from './guard/auth-guard';
import { CycleSetup } from './pages/cycle-setup/cycle-setup';

export const routes: Routes = [
    {path:'',component:Auth},
    { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
    {path:'cycle-setup',component:CycleSetup}
];
