import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LogOffFromWidgetComponent } from './log-off-from-widget/log-off-from-widget.component';
@Component({
  selector: 'app-widget-navbar',
  templateUrl: './widget-navbar.component.html',
  styleUrls: ['./widget-navbar.component.css']
})
export class WidgetNavbarComponent {
constructor(private route:Router,public dialog:MatDialog){}
logOut(){
  // alert("Logging off");
  const dialogRef= this.dialog.open(LogOffFromWidgetComponent)
  dialogRef.afterClosed().subscribe((res)=>{
    console.log(res);
    if(res=='yes')
    {
      sessionStorage.clear();
      this.route.navigate([""]);
         }
        //  else if(res=='no')
        //  {
        //   this.route.navigate(["card-widget"])
        //  }
        })
    }

  
  // this.route.navigate(["card-widget"])
}

