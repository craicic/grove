import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CreatorPickerComponent} from './creator-picker.component';

describe('CreatorPickerComponent', () => {
  let component: CreatorPickerComponent;
  let fixture: ComponentFixture<CreatorPickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatorPickerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatorPickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
