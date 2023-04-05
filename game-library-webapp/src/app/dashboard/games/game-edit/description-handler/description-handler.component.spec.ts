import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DescriptionHandlerComponent} from './description-handler.component';

describe('DescriptionHandlerComponent', () => {
  let component: DescriptionHandlerComponent;
  let fixture: ComponentFixture<DescriptionHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DescriptionHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DescriptionHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
