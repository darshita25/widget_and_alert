import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Alertmessage } from 'src/app/alertmessage';
import { ServicealertService } from 'src/app/servicealert.service';

@Component({
  selector: 'app-notifications-child',
  templateUrl: './notifications-child.component.html',
  styleUrls: ['./notifications-child.component.css']
})
export class NotificationsChildComponent {
  @Input() curRow=new Set<Alertmessage>();
  @Output() closeEvent=new EventEmitter<any>();
  show!: boolean;
  messages:Alertmessage={
    messageId: "",
    aircraftRegistration: "",
    flight: "",
    desk: "",
    deskCategory: "",
    escalated: "",
    acknowledge: "",
    acknowledgedBy: "",
    received: "",
    priority: "",
    isPublished: 0
  }
  ngOnInit(): void {
    this.show=false;
  }
  constructor(private router:Router,private message:ServicealertService)
  {

  }
  toggle()
  {
    this.show=!this.show;
  }
  acknowledgeData(data:Alertmessage)
  {
    this.message.updateAck(data).subscribe(()=>{this.onClose();
  })
  }
  onClose()
  {
   this.router.navigate(['/notifications'])
  .then(() => {
    window.location.reload();
  });
  // this.closeEvent.emit();
  }
}
