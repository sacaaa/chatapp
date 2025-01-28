import {
    Component,
    AfterViewInit,
    AfterViewChecked,
    ViewChild,
    ElementRef,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReceivedMessage } from '../../../../core/models/received-message';
import { ChatService } from '../../../../core/services/chat.service';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../../core/services/auth.service';
import { ChatRoom } from '../../../../core/models/chat-room';

@Component({
    selector: 'app-chat-room',
    imports: [CommonModule, FormsModule],
    templateUrl: './chat-room.component.html',
})
export class ChatRoomComponent implements AfterViewInit, AfterViewChecked {
    messages: ReceivedMessage[] = [];
    newMessage: string = '';
    chatRoom!: ChatRoom;
    currentUserId!: number;
    isLoaded: boolean = false;

    // ViewChild az üzenetek konténeréhez
    @ViewChild('messagesContainer') private messagesContainer!: ElementRef;

    constructor(
        private chatService: ChatService,
        private route: ActivatedRoute,
        private authService: AuthService,
    ) {}

    async ngOnInit(): Promise<void> {
        try {
            const userId = this.authService.getIdFromToken();
            if (!userId) {
                throw new Error('Failed to retrieve user ID from token.');
            }
            this.currentUserId = userId;

            const chatRoom = await this.chatService.getChatRoomById(
                Number(this.route.snapshot.paramMap.get('id')),
            );
            if (!chatRoom) {
                throw new Error('Chat room not found.');
            }
            this.chatRoom = chatRoom;

            await this.chatService.loadMessages(this.chatRoom.id);

            this.chatService.getMessages().subscribe((messages) => {
                this.messages = messages;
                this.scrollToBottom();
            });

            await this.chatService.joinChatRoom(this.chatRoom.id);
            console.log(
                `Chat room ${this.chatRoom.id} initialized successfully.`,
            );
            this.isLoaded = true;
        } catch (error) {
            console.error('Failed to initialize chat room:', error);
        }
    }

    async sendMessage(): Promise<void> {
        if (this.newMessage.trim()) {
            const senderId = this.authService.getIdFromToken();
            if (!senderId) {
                console.error('No sender ID available.');
                return;
            }
            await this.chatService.sendMessage(senderId, this.newMessage);
            this.newMessage = '';
            this.scrollToBottom(); // Küldés után görgetés
        }
    }

    ngOnDestroy(): void {
        this.chatService.leaveChatRoom();
    }

    ngAfterViewInit(): void {
        // Komponens betöltése után görgetés a legaljára
        this.scrollToBottom();
    }

    ngAfterViewChecked(): void {
        // Görgetés biztosítása minden változásnál
        this.scrollToBottom();
    }

    private scrollToBottom(): void {
        if (this.messagesContainer) {
            try {
                this.messagesContainer.nativeElement.scrollTop =
                    this.messagesContainer.nativeElement.scrollHeight;
            } catch (err) {
                console.error('Failed to scroll to bottom:', err);
            }
        }
    }
}
