import {ComponentFixture, TestBed} from '@angular/core/testing';

import {StuffHandlerComponent} from './stuff-handler.component';

describe('StuffHandlerComponent', () => {
  let component: StuffHandlerComponent;
  let fixture: ComponentFixture<StuffHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StuffHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StuffHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
