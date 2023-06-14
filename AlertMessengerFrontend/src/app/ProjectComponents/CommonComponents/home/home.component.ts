import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LogOffComponent } from '../../log-off/log-off.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  constructor(private router:Router,public dialog:MatDialog){}
  ngOnInit(): void {
    if(sessionStorage.getItem('role')!=='ADMIN' || sessionStorage.getItem('role')===null){
      this.router.navigate([""]);
    }
  }

  logOut(){
    // alert("Logging off");
    const dialogRef= this.dialog.open(LogOffComponent)
    dialogRef.afterClosed().subscribe((res)=>{
      if(res=='yes')
      {
      sessionStorage.clear();
      this.router.navigate([""]);
      }
    })
  }
}
