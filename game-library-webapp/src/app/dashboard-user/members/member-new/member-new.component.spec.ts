import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MemberNewComponent} from './member-new.component';

describe('MemberNewComponent', () => {
  let component: MemberNewComponent;
  let fixture: ComponentFixture<MemberNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MemberNewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
