import { Component,OnInit } from '@angular/core';
import { ServiceWidgetService } from 'src/app/service-widget.service';
import { Router } from '@angular/router';
import { WidgetManager } from 'src/app/widgetmanager';
import { FormBuilder,Validators,FormControl, FormGroup } from '@angular/forms';

interface Options{
  value1:string;
  viewValue1:string;
}
interface Size{
  value:string;
  viewValue:string;
}
@Component({
  selector: 'app-add-widgets',
  templateUrl: './add-widgets.component.html',
  styleUrls: ['./add-widgets.component.css']
})
export class AddWidgetsComponent implements OnInit{

buttonForm=new FormGroup({ 
validateForm:new FormControl('',[
  Validators.required
]),
validateForm1:new FormControl('',[
  Validators.required,
]),
validateForm2:new FormControl('',[
  Validators.required,
]),
validateForm3:new FormControl('',[
  Validators.required,
]),
validateForm4:new FormControl('',[
  Validators.required,
]),
})

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
  form:WidgetManager={
    widgetId: '', 
    widgetName: '',
    widgetTag :'' ,
    widgetStatus:'',
    widgetSize:'',
    description:''
  }
  constructor(private forms:ServiceWidgetService,private router:Router)
  {

  }

  ngOnInit(): void {
    if(sessionStorage.getItem('role')!=='ADMIN' || sessionStorage.getItem('role')===null){
      this.router.navigate([""]);
    }
  }
  addData()
  {
    
       this.forms.create1(this.form).subscribe(()=>{
        this.router.navigate(["/card-widget"])
       })
       
  }
  }
