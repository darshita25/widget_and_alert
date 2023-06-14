import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { Alertmessage } from 'src/app/alertmessage';
import { ServicealertService } from 'src/app/servicealert.service';


@Component({
  selector: 'app-edit-component',
  templateUrl: './edit-component.component.html',
  styleUrls: ['./edit-component.component.css']
})
export class EditComponentComponent implements OnInit {
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
  constructor(private message:ServicealertService,private router:ActivatedRoute,private route:Router)
  {
  
  }
  ngOnInit(): void {
    this.router.paramMap.subscribe((params)=>{
      let id=String(params.get('id'))
      this.getById(id)
      
    if(sessionStorage.getItem('role')!=='ADMIN' || sessionStorage.getItem('role')===null){
      this.route.navigate([""]);
    }

    
   })
  }
  getById(id:string)
  {
    this.message.getbyId(id).subscribe((data)=>{
      this.messages=data;
    })
  }
  updateData()
  {
    this.message.update(this.messages).subscribe(()=>{this.route.navigate(['/card-component'])
  })
  }
  onCancel()
  {
  this.route.navigate(["/card-component"])
  }
}
