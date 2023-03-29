import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NewGameCoreSummaryComponent} from './new-game-core-summary.component';

describe('NewGameCoreSummaryComponent', () => {
  let component: NewGameCoreSummaryComponent;
  let fixture: ComponentFixture<NewGameCoreSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewGameCoreSummaryComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGameCoreSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
