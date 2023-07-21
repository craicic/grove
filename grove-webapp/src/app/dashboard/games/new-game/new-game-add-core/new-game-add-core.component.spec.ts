import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewGameAddCoreComponent} from './new-game-add-core.component';

describe('NewGameAddCoreComponent', () => {
  let component: NewGameAddCoreComponent;
  let fixture: ComponentFixture<NewGameAddCoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewGameAddCoreComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGameAddCoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
