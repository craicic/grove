import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SimpleFilterFormComponent} from './simple-filter-form.component';

describe('SimpleFilterFormComponent', () => {
  let component: SimpleFilterFormComponent;
  let fixture: ComponentFixture<SimpleFilterFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SimpleFilterFormComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimpleFilterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
