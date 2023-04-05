import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SizeHandlerComponent} from './size-handler.component';

describe('SizeHandlerComponent', () => {
  let component: SizeHandlerComponent;
  let fixture: ComponentFixture<SizeHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SizeHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SizeHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
