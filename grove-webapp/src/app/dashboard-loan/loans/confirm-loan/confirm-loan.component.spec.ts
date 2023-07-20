import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ConfirmLoanComponent} from './confirm-loan.component';

describe('ConfirmLoanComponent', () => {
  let component: ConfirmLoanComponent;
  let fixture: ComponentFixture<ConfirmLoanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConfirmLoanComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmLoanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
