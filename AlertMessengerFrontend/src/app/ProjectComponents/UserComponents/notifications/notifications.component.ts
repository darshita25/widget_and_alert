import { AfterViewInit, Component,OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationService } from '../../../notification.service';
import { Alertmessage } from 'src/app/alertmessage';
import { MatTableDataSource } from '@angular/material/table';
import { ServicealertService } from '../../../servicealert.service';
import { MatSort, Sort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { LogOffComponent } from '../../log-off/log-off.component';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit, AfterViewInit {
  allMessages: Alertmessage[] = [];
  messages: Alertmessage = {
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
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)

  sort!: MatSort;
  showSummary=false;
  hidden = false;
  toggleBadgeVisibility() {
    this.hidden = !this.hidden;
  }
  clickedRows = new Set<Alertmessage>();
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
  ];
  displayedColumn: string[] = [
    "aircraftRegistration",
    "flight",
    "desk",
    "deskCategory",
    "acknowledgedBy",
    "received",
    "priority",
  ];
  dataSource = new MatTableDataSource<Alertmessage>();

  constructor(private route: Router, public notification: NotificationService, private message: ServicealertService,public dialog:MatDialog) {
  }
  ngAfterViewInit(): void {
    this.dataSource.sort=this.sort;
    // this.paginator.pageSize=5;
    this.paginator.pageIndex=0;
    this.dataSource.paginator = this.paginator;
  }
  publishedMessages!: Alertmessage[];
  unreadMessages!: Alertmessage[];
  ngOnInit(): void {
    if (sessionStorage.getItem('role') !== 'USER' || sessionStorage.getItem('role') === null) {
      this.route.navigate([""]);
    }

    this.message.getUnreadData().subscribe((res) => {
      this.unreadMessages = res;
    })

    //TODO make an api call to get messages for user from db(published messages)
    this.message.getPublishedData().subscribe((res) => {
      this.publishedMessages = res;
      this.dataSource.data = this.publishedMessages;
    })
    this.notification.messageSubject.asObservable().subscribe((res) => {
      this.publishedMessages = [...this.publishedMessages, res as Alertmessage];
      this.dataSource.data = this.publishedMessages;
      this.getUnread();
    })

   

    
    
  }
 
  applyFilter(filterValue:string)
  {
  this.dataSource.filter=filterValue.trim().toLocaleLowerCase();
  }

  logOut() {
    // alert("Logging off");
    const dialogRef= this.dialog.open(LogOffComponent)
    dialogRef.afterClosed().subscribe((res)=>{
      if(res=='yes')
      {
      sessionStorage.clear();
      this.route.navigate([""]);
      }
      // else if(res=='no')
      // {
      //   this.route.navigate(["/"]);
      // }
    })
  }
  
  closeSummary()
  {
    this.showSummary=true;
  }
  getUnread(){
    this.message.getUnreadData().subscribe((res) => {
      this.unreadMessages = res;
    })
  }
}