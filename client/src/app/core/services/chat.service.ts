import { Injectable } from '@angular/core';
import { BehaviorSubject, lastValueFrom, Observable } from 'rxjs';
import { ReceivedMessage } from '../models/received-message';
import { WebSocketService } from './web-socket.service';
import { SendedMessage } from '../models/sended-message';
import { HttpClient } from '@angular/common/http';
import { ChatRoom } from '../models/chat-room';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private messagesSubject: BehaviorSubject<ReceivedMessage[]> = new BehaviorSubject<ReceivedMessage[]>([]);
  private messages: ReceivedMessage[] = [];
  private currentChatRoomId: number | null = null;
  private readonly apiUrl = 'http://localhost:8080/api/chat';

  constructor(private webSocketService: WebSocketService,
              private http: HttpClient
  ) {}

  async joinChatRoom(chatRoomId: number): Promise<void> {
    if (this.currentChatRoomId !== null) {
      this.leaveChatRoom();
    }

    this.currentChatRoomId = chatRoomId;

    try {
      const topic = `/topic/chatroom/${chatRoomId}`;
      await this.webSocketService.subscribe(topic, (message: ReceivedMessage) => {
        message.createdAt = message.createdAt.replace('T', ' ').substring(0, 19);
        console.log('Received message:', message);
        this.addMessage(message);
      });
      console.log(`Joined chat room: ${chatRoomId}`);
    } catch (error) {
      console.error('Failed to join chat room:', error);
    }
  }

  async sendMessage(senderId: number, content: string): Promise<void> {
    if (this.currentChatRoomId === null) {
      console.error('No chat room joined to send messages.');
      return;
    }
  
    const message: SendedMessage = {
      content,
      chatRoomId: this.currentChatRoomId,
      senderId,
    };
  
    try {
      await lastValueFrom(this.http.post(`${this.apiUrl}/message/send`, message));
      console.log('Message sent successfully:', message);
    } catch (error) {
      console.error('Failed to send message:', error);
    }
  }  

  async loadMessages(chatRoomId: number): Promise<void> {
    try {
      const url = `${this.apiUrl}/message/receive/${chatRoomId}`;
      const receivedMessages = await lastValueFrom(this.http.get<ReceivedMessage[]>(url));

      receivedMessages.map((message) => {
        message.createdAt = message.createdAt.replace('T', ' ').substring(0, 19);
        return message;
      });

      this.messages = receivedMessages;
      this.messagesSubject.next(this.messages);

      console.log(`Loaded messages for chat room ${chatRoomId}:`, receivedMessages);
    } catch (error) {
      console.error('Failed to load messages:', error);
    }
  }

  async getChatRooms(): Promise<ChatRoom[]> {
    try {
      const url = `${this.apiUrl}/chat-rooms`;
      const chatRooms = await lastValueFrom(this.http.get<ChatRoom[]>(url));
      console.log('Fetched chat rooms:', chatRooms);
      return chatRooms;
    } catch (error) {
      console.error('Failed to fetch chat rooms:', error);
      return [];
    }
  }

  async getChatRoomById(chatRoomId: number): Promise<ChatRoom | null> {
    try {
      const chatRooms = await this.getChatRooms();
      const chatRoom = chatRooms.find((room) => room.id === chatRoomId) || null;
      console.log('Fetched chat room:', chatRoom);
      return chatRoom;
    } catch (error) {
      console.error('Failed to fetch chat room:', error);
      return null;
    }
  }

  leaveChatRoom(): void {
    if (this.currentChatRoomId !== null) {
      this.webSocketService.disconnect();
      this.currentChatRoomId = null;
      this.messages = [];
      this.messagesSubject.next(this.messages);
      console.log(`Left chat room`);
    }
  }

  getMessages(): Observable<ReceivedMessage[]> {
    return this.messagesSubject.asObservable();
  }

  private addMessage(message: ReceivedMessage): void {
    this.messages.push(message);
    this.messagesSubject.next(this.messages);
  }
}
