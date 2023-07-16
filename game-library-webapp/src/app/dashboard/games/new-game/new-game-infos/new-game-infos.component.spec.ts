import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewGameInfosComponent} from './new-game-infos.component';

describe('NewGameInfosComponent', () => {
  let component: NewGameInfosComponent;
  let fixture: ComponentFixture<NewGameInfosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewGameInfosComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGameInfosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
