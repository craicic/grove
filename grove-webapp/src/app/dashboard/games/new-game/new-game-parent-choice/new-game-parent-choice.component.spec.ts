import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewGameParentChoiceComponent} from './new-game-parent-choice.component';

describe('NewGameParentChoiceComponent', () => {
  let component: NewGameParentChoiceComponent;
  let fixture: ComponentFixture<NewGameParentChoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewGameParentChoiceComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGameParentChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
