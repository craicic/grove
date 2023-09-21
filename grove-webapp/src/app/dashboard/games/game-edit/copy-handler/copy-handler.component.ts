import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {GameCopy} from '../../../../model/game-copy.model';
import {Publisher} from '../../../../model/publisher.model';
import {GeneralStateEnum} from '../../../../model/enum/general-state.enum';

@Component({
  selector: 'app-copy-handler',
  templateUrl: './copy-handler.component.html',
  styleUrls: ['./copy-handler.component.css']
})
export class CopyHandlerComponent implements OnInit {

  gc: GameCopy;
  pb: Publisher;
  actualEnumType: typeof GeneralStateEnum = GeneralStateEnum;
  stateList: Array<string> = Object.keys(GeneralStateEnum);

  form = this.fb.group({
    objectCode: ['', Validators.required],
    generalState: [this.stateList[0], Validators.required],
    wearCondition: ['Neuf', Validators.required],
    location: [''],
    availableForLoan: [true],
    publisher: this.fb.group({
      name: ['']
    })
  });

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
  }
}
