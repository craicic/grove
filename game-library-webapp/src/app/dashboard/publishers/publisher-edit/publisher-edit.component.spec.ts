import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PublisherEditComponent} from './publisher-edit.component';

describe('PublisherEditComponent', () => {
  let component: PublisherEditComponent;
  let fixture: ComponentFixture<PublisherEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PublisherEditComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PublisherEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
