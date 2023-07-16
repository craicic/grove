import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DashboardLoanComponent} from './dashboard-loan.component';

describe('DashboardLoanComponent', () => {
  let component: DashboardLoanComponent;
  let fixture: ComponentFixture<DashboardLoanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardLoanComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardLoanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
