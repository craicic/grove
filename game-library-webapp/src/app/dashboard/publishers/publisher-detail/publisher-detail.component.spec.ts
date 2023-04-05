import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PublisherDetailComponent} from './publisher-detail.component';

describe('PublisherDetailComponent', () => {
  let component: PublisherDetailComponent;
  let fixture: ComponentFixture<PublisherDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PublisherDetailComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PublisherDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
