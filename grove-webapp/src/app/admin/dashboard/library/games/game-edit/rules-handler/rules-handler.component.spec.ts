import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RulesHandlerComponent } from './rules-handler.component';

describe('RulesHandlerComponent', () => {
  let component: RulesHandlerComponent;
  let fixture: ComponentFixture<RulesHandlerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RulesHandlerComponent]
    });
    fixture = TestBed.createComponent(RulesHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
