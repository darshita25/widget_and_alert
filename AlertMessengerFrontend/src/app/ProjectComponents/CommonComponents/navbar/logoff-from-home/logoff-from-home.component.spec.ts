import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoffFromHomeComponent } from './logoff-from-home.component';

describe('LogoffFromHomeComponent', () => {
  let component: LogoffFromHomeComponent;
  let fixture: ComponentFixture<LogoffFromHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogoffFromHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogoffFromHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
