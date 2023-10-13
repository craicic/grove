import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WipComponent } from './wip.component';

describe('WipComponent', () => {
  let component: WipComponent;
  let fixture: ComponentFixture<WipComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WipComponent]
    });
    fixture = TestBed.createComponent(WipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
