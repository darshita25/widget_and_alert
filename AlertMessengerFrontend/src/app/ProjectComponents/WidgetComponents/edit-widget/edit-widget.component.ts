import { Component,OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceWidgetService } from 'src/app/service-widget.service';
import { WidgetManager } from 'src/app/widgetmanager';

interface Options{
  value1:string;
  viewValue1:string;
}
interface Size{
  value:string;
  viewValue:string;
}
@Component({
  selector: 'app-edit-widget',
  templateUrl: './edit-widget.component.html',
  styleUrls: ['./edit-widget.component.css']
})
export class EditWidgetComponent {
  options:Options[]=[
    {value1:'Enabled',viewValue1:'Enabled'},
    {value1:'Disabled',viewValue1:'Disabled'}
  ];
  size:Size[]=[
    {value:'1 x',viewValue:'1 x'},
    {value:'2 x',viewValue:'2 x'},
    {value:'3 x',viewValue:'3 x'},
    {value:'4 x',viewValue:'4 x'}
  ];
 forms:WidgetManager={
 widgetId:"",
 widgetName:"",
 widgetTag:"",
 widgetStatus:"",
 widgetSize:"",
 description:"",
}
constructor(private form:ServiceWidgetService,private router:ActivatedRoute,private route:Router)
{
  
}
ngOnInit(): void {
  if(sessionStorage.getItem('role')!=='ADMIN' || sessionStorage.getItem('role')===null){
    this.route.navigate([""]);
  }
  
  this.router.paramMap.subscribe((params)=>{
  let widgetId=String(params.get('widgetId'))
  this.getbyId1(widgetId)
 })
}
getbyId1(widgetId:string)
{
  this.form.getbyId1(widgetId).subscribe((data)=>{
    this.forms=data;
  })
}
updateData()
{
  this.form.update1(this.forms).subscribe(()=>{this.route.navigate(['/card-widget'])
})
}
}
