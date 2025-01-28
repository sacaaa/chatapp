import { Injectable } from '@angular/core';
import { Client, IMessage, StompSubscription } from '@stomp/stompjs';
import { SendedMessage } from '../models/sended-message';

@Injectable({
    providedIn: 'root',
})
export class WebSocketService {
    private client: Client;
    private connected: boolean = false;
    private subscriptions: Map<string, StompSubscription> = new Map();

    constructor() {
        this.client = new Client({
            brokerURL: 'ws://localhost:8080/ws',
            reconnectDelay: 5000,
        });

        this.client.onConnect = () => {
            console.log('WebSocket connected');
            this.connected = true;
        };

        this.client.onStompError = (error) => {
            console.error('WebSocket error:', error);
            this.connected = false;
        };

        this.client.activate();
    }

    private ensureConnected(): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            if (this.connected) {
                resolve();
            } else {
                this.client.onConnect = () => {
                    console.log('WebSocket connected');
                    this.connected = true;
                    resolve();
                };

                this.client.onStompError = (error) => {
                    console.error('STOMP error:', error);
                    reject(error);
                };
            }
        });
    }

    async subscribe(
        topic: string,
        callback: (message: any) => void,
    ): Promise<void> {
        await this.ensureConnected();
        if (this.subscriptions.has(topic)) {
            console.warn(`Already subscribed to topic: ${topic}`);
            return;
        }

        const subscription = this.client.subscribe(
            topic,
            (message: IMessage) => {
                callback(JSON.parse(message.body));
            },
        );
        this.subscriptions.set(topic, subscription);
    }

    async send(destination: string, body: SendedMessage): Promise<void> {
        await this.ensureConnected();
        if (this.client.connected) {
            this.client.publish({ destination, body: JSON.stringify(body) });
        } else {
            console.error('WebSocket is not connected');
        }
    }

    disconnect(): void {
        this.subscriptions.forEach((subscription) =>
            subscription.unsubscribe(),
        );
        this.subscriptions.clear();
        this.client.deactivate();
        this.connected = false;
    }
}
