import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PublisherHandlerComponent} from './publisher-handler.component';

describe('PublisherHandlerComponent', () => {
  let component: PublisherHandlerComponent;
  let fixture: ComponentFixture<PublisherHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PublisherHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PublisherHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
