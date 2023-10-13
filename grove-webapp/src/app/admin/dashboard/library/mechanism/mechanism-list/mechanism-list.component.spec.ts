import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MechanismListComponent} from './mechanism-list.component';

describe('MechanismListComponent', () => {
  let component: MechanismListComponent;
  let fixture: ComponentFixture<MechanismListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MechanismListComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MechanismListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
