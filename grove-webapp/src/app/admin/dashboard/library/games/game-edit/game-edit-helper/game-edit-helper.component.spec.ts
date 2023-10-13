import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GameEditHelperComponent} from './game-edit-helper.component';

describe('GameEditHelperComponent', () => {
  let component: GameEditHelperComponent;
  let fixture: ComponentFixture<GameEditHelperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GameEditHelperComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GameEditHelperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
