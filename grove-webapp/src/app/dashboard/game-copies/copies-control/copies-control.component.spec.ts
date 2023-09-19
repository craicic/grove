import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CopiesControlComponent } from './copies-control.component';

describe('CopiesControlComponent', () => {
  let component: CopiesControlComponent;
  let fixture: ComponentFixture<CopiesControlComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CopiesControlComponent]
    });
    fixture = TestBed.createComponent(CopiesControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
