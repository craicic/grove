import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MechanismsComponent} from './mechanisms.component';

describe('MechanismsComponent', () => {
  let component: MechanismsComponent;
  let fixture: ComponentFixture<MechanismsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MechanismsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MechanismsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
