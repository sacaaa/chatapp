import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { ChatService } from '../services/chat.service';

export const chatRoomGuard: CanActivateFn = async (route, state) => {
  const router = inject(Router);
  const chatService = inject(ChatService);

  const chatRoomId = Number(route.paramMap.get('id'));

  if (isNaN(chatRoomId)) {
    router.navigate(['/chatrooms']);
    return false;
  }

  try {
    const chatRooms = await chatService.getChatRooms();
    const exists = chatRooms.some((room) => room.id === chatRoomId);

    if (!exists) {
      router.navigate(['/chatrooms']);
      return false;
    }

    return true;
  } catch (error) {
    console.error('Error checking chat room:', error);
    router.navigate(['/chatrooms']);
    return false;
  }
};
