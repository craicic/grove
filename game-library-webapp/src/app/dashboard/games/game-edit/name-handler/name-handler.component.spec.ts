import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NameHandlerComponent} from './name-handler.component';

describe('NameHandlerComponent', () => {
  let component: NameHandlerComponent;
  let fixture: ComponentFixture<NameHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NameHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NameHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
