import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CreatorHandlerComponent} from './creator-handler.component';

describe('CreatorHandlerComponent', () => {
  let component: CreatorHandlerComponent;
  let fixture: ComponentFixture<CreatorHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatorHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatorHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
