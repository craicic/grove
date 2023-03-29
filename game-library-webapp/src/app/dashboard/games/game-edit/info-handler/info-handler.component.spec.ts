import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InfoHandlerComponent} from './info-handler.component';

describe('InfoHandlerComponent', () => {
  let component: InfoHandlerComponent;
  let fixture: ComponentFixture<InfoHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InfoHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
