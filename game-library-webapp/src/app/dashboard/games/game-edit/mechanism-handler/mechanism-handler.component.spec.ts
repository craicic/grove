import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MechanismHandlerComponent} from './mechanism-handler.component';

describe('MechanismHandlerComponent', () => {
  let component: MechanismHandlerComponent;
  let fixture: ComponentFixture<MechanismHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MechanismHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MechanismHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
