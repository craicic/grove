import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CopyHandlerComponent } from './copy-handler.component';

describe('CopyHandlerComponent', () => {
  let component: CopyHandlerComponent;
  let fixture: ComponentFixture<CopyHandlerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CopyHandlerComponent]
    });
    fixture = TestBed.createComponent(CopyHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
