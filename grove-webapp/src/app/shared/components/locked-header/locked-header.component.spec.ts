import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LockedHeaderComponent} from './locked-header.component';

describe('LockedHeaderComponent', () => {
  let component: LockedHeaderComponent;
  let fixture: ComponentFixture<LockedHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LockedHeaderComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LockedHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
