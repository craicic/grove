import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GameEditWrapperComponent} from './game-edit-wrapper.component';

describe('LockedModeWrapperComponent', () => {
  let component: GameEditWrapperComponent;
  let fixture: ComponentFixture<GameEditWrapperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GameEditWrapperComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GameEditWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
