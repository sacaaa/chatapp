import { Routes } from '@angular/router';
import { ChatRoomListComponent } from './modules/chat-room-list/pages/chat-room-list/chat-room-list.component';
import { ChatRoomComponent } from './modules/chat-room/pages/chat-room/chat-room.component';
import { chatRoomGuard } from './core/guards/chat-room.guard';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './modules/auth/pages/login/login.component';
import { RegisterComponent } from './modules/auth/pages/register/register.component';
import { ProfileComponent } from './modules/profile/pages/profile/profile.component';

export const routes: Routes = [
    { path: '', redirectTo: 'chatrooms', pathMatch: 'full' },
    { path: 'chatrooms', component: ChatRoomListComponent },
    {
        path: 'chatrooms/:id',
        component: ChatRoomComponent,
        canActivate: [authGuard, chatRoomGuard],
    },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
    { path: '**', redirectTo: '' },
];
