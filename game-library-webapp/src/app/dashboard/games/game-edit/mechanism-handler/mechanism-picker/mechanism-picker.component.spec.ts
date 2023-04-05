import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MechanismPickerComponent} from './mechanism-picker.component';

describe('MechanismPickerComponent', () => {
  let component: MechanismPickerComponent;
  let fixture: ComponentFixture<MechanismPickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MechanismPickerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MechanismPickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
