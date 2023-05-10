import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MaterialHandlerComponent} from './material-handler.component';

describe('MaterialHandlerComponent', () => {
  let component: MaterialHandlerComponent;
  let fixture: ComponentFixture<MaterialHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MaterialHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MaterialHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
