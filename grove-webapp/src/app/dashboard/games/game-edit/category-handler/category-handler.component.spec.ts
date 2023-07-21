import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CategoryHandlerComponent} from './category-handler.component';

describe('CategoryHandlerComponent', () => {
  let component: CategoryHandlerComponent;
  let fixture: ComponentFixture<CategoryHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategoryHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
