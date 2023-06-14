import { AfterViewInit, Component,ComponentRef,OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Alertmessage } from 'src/app/alertmessage';
import { ServicealertService } from 'src/app/servicealert.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort} from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { NotificationService } from 'src/app/notification.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit,AfterViewInit {
allMessages:Alertmessage[]=[];

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
displayedColumns: string[] = [
"aircraftRegistration",
"flight",
"desk",
"deskCategory",
"escalated",
"acknowledge",
"acknowledgedBy",
"received",
"priority",
"Actions"
];
displayedColumn: string[] = [
  "aircraftRegistration",
  "flight",
  "desk",
  "deskCategory",
  "acknowledgedBy",
  "received",
  "priority",
  "Actions"
  ];
clickedRows = new Set<Alertmessage>();

popupState!: Boolean;
selected:any;
dataSource=new MatTableDataSource<Alertmessage>();
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
   sort!: MatSort;

constructor(private message:ServicealertService,private route:Router, public notification: NotificationService){

}

input:any;
ngOnInit(): void {
  if(sessionStorage.getItem('role')!=='ADMIN' || sessionStorage.getItem('role')===null){
    this.route.navigate([""]);
  }
  this.getAllMessages();
}
ngAfterViewInit()
{
this.dataSource.sort=this.sort;
// this.paginator.pageSize=5;
this.paginator.pageIndex=0;
this.dataSource.paginator = this.paginator;
}
showSpinner = true;
getAllMessages()
{
    this.message.getAll().subscribe((data)=>{
    this.dataSource.data=data;
  })
}
publishData(element:Alertmessage)
{
  if (element) {  
      this.message.publish(element).subscribe(()=>{this.route.navigate(['/card-component'])})
      this.notification.sendMessage(element);
    }

}
applyFilter(filterValue:string)
{
  this.dataSource.filter=filterValue.trim().toLocaleLowerCase();
}
}
