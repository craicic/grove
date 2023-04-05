import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewGameBasicsComponent} from './new-game-basics.component';

describe('NewGameComponent', () => {
  let component: NewGameBasicsComponent;
  let fixture: ComponentFixture<NewGameBasicsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewGameBasicsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGameBasicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
