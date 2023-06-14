import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatToolbarModule} from '@angular/material/toolbar';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {CardComponent } from './ProjectComponents/AlertComponents/card/card.component';
import { HttpClientModule } from '@angular/common/http';
import {MatTableModule} from '@angular/material/table';
import { AddMessageComponent } from './ProjectComponents/AlertComponents/add-message/add-message.component'; 
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import { FormsModule } from '@angular/forms';
import { DisplayMessageComponent } from './ProjectComponents/AlertComponents/card/display-message/display-message.component';
import { EditComponentComponent } from './ProjectComponents/AlertComponents/edit-component/edit-component.component';
import {MatDialogModule} from '@angular/material/dialog';
import { HomeComponent } from './ProjectComponents/CommonComponents/home/home.component';
import { NavbarComponent } from './ProjectComponents/CommonComponents/navbar/navbar.component';
import { NotificationsComponent } from './ProjectComponents/UserComponents/notifications/notifications.component';
import { LoginpageComponent } from './ProjectComponents/CommonComponents/loginpage/loginpage.component';
import { CardWidgetComponent } from './ProjectComponents/WidgetComponents/card-widget/card-widget.component';
import { AddWidgetsComponent } from './ProjectComponents/WidgetComponents/add-widgets/add-widgets.component';
import { WidgetNavbarComponent } from './ProjectComponents/WidgetComponents/widget-navbar/widget-navbar.component';
import { MatSelectModule } from '@angular/material/select';
import { EditWidgetComponent } from './ProjectComponents/WidgetComponents/edit-widget/edit-widget.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { NgxUiLoaderHttpModule, NgxUiLoaderModule } from 'ngx-ui-loader';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatBadgeModule} from '@angular/material/badge';
import { NotificationsChildComponent } from './ProjectComponents/UserComponents/notifications/notifications-child/notifications-child.component';
import { DeleteRowComponent } from './ProjectComponents/WidgetComponents/card-widget/delete-row/delete-row.component';
import { LogOffFromWidgetComponent } from './ProjectComponents/WidgetComponents/widget-navbar/log-off-from-widget/log-off-from-widget.component';
import { LogoffFromHomeComponent } from './ProjectComponents/CommonComponents/navbar/logoff-from-home/logoff-from-home.component';
import { LogOffComponent } from './ProjectComponents/log-off/log-off.component';


@NgModule({
  declarations: [
    AppComponent,
    CardComponent,
    AddMessageComponent,
    DisplayMessageComponent,
    EditComponentComponent,
    HomeComponent,
    NavbarComponent,
    NotificationsComponent,
    LoginpageComponent,
    CardWidgetComponent,
    AddWidgetsComponent,
    EditWidgetComponent,
    WidgetNavbarComponent,
    NotificationsChildComponent,
    DeleteRowComponent,
    LogOffFromWidgetComponent,
    LogoffFromHomeComponent,
    LogOffComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    HttpClientModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    FormsModule,
    MatDialogModule,
    MatSelectModule,
    ReactiveFormsModule,
    NgxUiLoaderModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressBarModule,
    MatBadgeModule,
    MatProgressSpinnerModule,
    NgxUiLoaderHttpModule.forRoot({
      showForeground:true,
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
