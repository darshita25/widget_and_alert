import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CardComponent } from './ProjectComponents/AlertComponents/card/card.component';
import { AddMessageComponent } from './ProjectComponents/AlertComponents/add-message/add-message.component';
import { EditComponentComponent } from './ProjectComponents/AlertComponents/edit-component/edit-component.component';
import { HomeComponent } from './ProjectComponents/CommonComponents/home/home.component';
import { NotificationsComponent } from './ProjectComponents/UserComponents/notifications/notifications.component';
import { LoginpageComponent } from './ProjectComponents/CommonComponents/loginpage/loginpage.component';
import { CardWidgetComponent } from './ProjectComponents/WidgetComponents/card-widget/card-widget.component';
import { AddWidgetsComponent } from './ProjectComponents/WidgetComponents/add-widgets/add-widgets.component';
import { EditWidgetComponent } from './ProjectComponents/WidgetComponents/edit-widget/edit-widget.component';
import { DeleteRowComponent } from './ProjectComponents/WidgetComponents/card-widget/delete-row/delete-row.component';

const routes: Routes = [
  {
    path:'notifications',component:NotificationsComponent
  },
  {
    path:'card-widget',component:CardWidgetComponent
  },
  {
    path:'',component:LoginpageComponent
  },
  {
    path:'home-page',component:HomeComponent
  },
  {
  path:'card-component',component:CardComponent},
  {
  path:'add-message',component:AddMessageComponent
  },
  {
    path:'edit-component/:id',component:EditComponentComponent
  },
  {
    path:'edit-widget/:widgetId',component:EditWidgetComponent
  },
  {
    path:'add-widget',component:AddWidgetsComponent
  },
  {
    path:'card-component/:id',component:CardComponent
  },
  {
    path:'delete-row/:widgetId',component:DeleteRowComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
