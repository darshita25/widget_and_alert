import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LogoffFromHomeComponent } from './logoff-from-home/logoff-from-home.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent  {
constructor(private route:Router,public dialog:MatDialog){}

  logOut(){
    // alert("logging off");
    const dialogRef= this.dialog.open(LogoffFromHomeComponent)
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
}