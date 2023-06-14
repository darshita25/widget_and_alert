import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetNavbarComponent } from './widget-navbar.component';

describe('WidgetNavbarComponent', () => {
  let component: WidgetNavbarComponent;
  let fixture: ComponentFixture<WidgetNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WidgetNavbarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WidgetNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
