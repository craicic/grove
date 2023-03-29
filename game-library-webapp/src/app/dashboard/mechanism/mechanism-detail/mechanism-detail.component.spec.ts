import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MechanismDetailComponent} from './mechanism-detail.component';

describe('MechanismDetailComponent', () => {
  let component: MechanismDetailComponent;
  let fixture: ComponentFixture<MechanismDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MechanismDetailComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MechanismDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
