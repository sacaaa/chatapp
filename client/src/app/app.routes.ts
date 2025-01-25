import { Routes } from '@angular/router';
import { ChatRoomListComponent } from './modules/chat-room-list/pages/chat-room-list/chat-room-list.component';
import { ChatRoomComponent } from './modules/chat-room/pages/chat-room/chat-room.component';
import { chatRoomGuard } from './core/guards/chat-room.guard';

export const routes: Routes = [
  { path: 'chatrooms', component: ChatRoomListComponent },
  { path: 'chatrooms/:id', component: ChatRoomComponent, canActivate: [chatRoomGuard] },
  { path: '**', redirectTo: 'chatrooms' },
];
