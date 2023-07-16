import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TitleHandlerComponent} from './title-handler.component';

describe('TitleHandlerComponent', () => {
  let component: TitleHandlerComponent;
  let fixture: ComponentFixture<TitleHandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TitleHandlerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TitleHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
