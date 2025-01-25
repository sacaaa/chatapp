import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReceivedMessage } from '../../../../core/models/received-message';
import { ChatService } from '../../../../core/services/chat.service';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat-room',
  imports: [CommonModule, FormsModule],
  templateUrl: './chat-room.component.html'
})
export class ChatRoomComponent {
  messages: ReceivedMessage[] = [];
  newMessage: string = '';
  chatRoomId!: number;

  constructor(
    private chatService: ChatService,
    private route: ActivatedRoute
  ) {}

  async ngOnInit(): Promise<void> {
    try {
      this.chatRoomId = Number(this.route.snapshot.paramMap.get('id'));

      await this.chatService.loadMessages(this.chatRoomId);

      this.chatService.getMessages().subscribe((messages) => {
        this.messages = messages;
      });

      await this.chatService.joinChatRoom(this.chatRoomId);
      console.log(`Chat room ${this.chatRoomId} initialized successfully.`);
    } catch (error) {
      console.error('Failed to initialize chat room:', error);
    }
  }

  async sendMessage(): Promise<void> {
    if (this.newMessage.trim()) {
      const senderId = 1;
      await this.chatService.sendMessage(senderId, this.newMessage);
      this.newMessage = '';
    }
  }

  ngOnDestroy(): void {
    this.chatService.leaveChatRoom();
  }
}
