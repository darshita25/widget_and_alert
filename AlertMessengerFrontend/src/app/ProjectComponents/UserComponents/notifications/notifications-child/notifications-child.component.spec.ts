import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationsChildComponent } from './notifications-child.component';

describe('NotificationsChildComponent', () => {
  let component: NotificationsChildComponent;
  let fixture: ComponentFixture<NotificationsChildComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotificationsChildComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationsChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
