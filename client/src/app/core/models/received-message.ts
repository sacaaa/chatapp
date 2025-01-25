export interface ReceivedMessage {
    id: number;
    content: string;
    chatRoomId: number;
    senderNickname: string;
    createdAt: string;
}