import { Routes } from '@angular/router';
import { Auth } from './pages/auth/auth';
import { Dashboard } from './pages/dashboard/dashboard';
import { authGuard } from './guard/auth-guard';
import { CycleSetup } from './pages/cycle-setup/cycle-setup';
import { CycleHistory } from './pages/cycle-history/cycle-history';
import { AiChat } from './pages/ai-chat/ai-chat';

export const routes: Routes = [
    {path:'',component:Auth},
    { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
    {path:'cycle-setup',component:CycleSetup,canActivate:[authGuard]},
    {path:'history',component:CycleHistory,canActivate:[authGuard]},
    {path:'ai-chat',component:AiChat,canActivate:[authGuard]}
];
