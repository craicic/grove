import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MechanismEditComponent} from './mechanism-edit.component';

describe('MechanismEditComponent', () => {
  let component: MechanismEditComponent;
  let fixture: ComponentFixture<MechanismEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MechanismEditComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MechanismEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
