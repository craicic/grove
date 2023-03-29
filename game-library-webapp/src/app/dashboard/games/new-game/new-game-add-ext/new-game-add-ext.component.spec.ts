import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewGameAddExtComponent} from './new-game-add-ext.component';

describe('NewGameAddExtComponent', () => {
  let component: NewGameAddExtComponent;
  let fixture: ComponentFixture<NewGameAddExtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewGameAddExtComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGameAddExtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
