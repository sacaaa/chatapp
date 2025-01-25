import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ChatRoom } from '../../../../core/models/chat-room';
import { ChatService } from '../../../../core/services/chat.service';

@Component({
  selector: 'app-chat-room-list',
  imports: [CommonModule],
  templateUrl: './chat-room-list.component.html'
})
export class ChatRoomListComponent {
  chatRooms: ChatRoom[] = [];

  constructor(private router: Router, private chatService: ChatService) {}

  async ngOnInit(): Promise<void> {
    try {
      this.chatRooms = await this.chatService.getChatRooms();
    } catch (error) {
      console.error('Failed to fetch chat rooms:', error);
    }
  }
}
