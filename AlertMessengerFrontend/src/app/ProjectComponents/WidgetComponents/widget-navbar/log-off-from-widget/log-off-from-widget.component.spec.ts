import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogOffFromWidgetComponent } from './log-off-from-widget.component';

describe('LogOffFromWidgetComponent', () => {
  let component: LogOffFromWidgetComponent;
  let fixture: ComponentFixture<LogOffFromWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogOffFromWidgetComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogOffFromWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
