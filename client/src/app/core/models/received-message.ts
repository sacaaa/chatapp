export interface ReceivedMessage {
    id: number;
    content: string;
    chatRoomId: number;
    senderId: number;
    senderNickname: string;
    senderAvatarId: number;
    createdAt: string;
}
