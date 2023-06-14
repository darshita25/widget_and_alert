import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  stompClient: any;

  constructor() {
    this.initializeWebSocketConnection();
  }
  messageSubject: Subject<any> = new Subject();
  msg: any = [];
  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8081/socket';
   // console.log("Establishing connection to  " + serverUrl);
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect({}, (frame: any) => {
      _this.stompClient.subscribe('/message', (message: any) => {
        if (message.body) {
          // this.msg.push(JSON.parse(message.body));
         
          this.messageSubject.next(JSON.parse(message.body))
        }
      });
    });
  }
  // getMessages() {
  //   return ;
  // }
  sendMessage(message: any) {
   // console.log("input from user " + message);
    this.stompClient.send('/app/send/message', {}, (JSON.stringify(message)));
  }
}
