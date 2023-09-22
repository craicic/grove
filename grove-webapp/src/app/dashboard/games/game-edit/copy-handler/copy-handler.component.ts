import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {GameCopy} from '../../../../model/game-copy.model';
import {Publisher} from '../../../../model/publisher.model';
import {GeneralStateEnum} from '../../../../model/enum/general-state.enum';
import {GameCopiesService} from '../../../game-copies/game-copies.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-copy-handler',
  templateUrl: './copy-handler.component.html',
  styleUrls: ['./copy-handler.component.css']
})
export class CopyHandlerComponent implements OnInit, OnDestroy {

  gc: GameCopy;
  pb: Publisher;
  actualEnumType: typeof GeneralStateEnum = GeneralStateEnum;
  stateList: Array<string> = Object.keys(GeneralStateEnum);
  private id: any;
  private paramSubscription: Subscription;

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

  constructor(private fb: FormBuilder,
              public service: GameCopiesService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    const copyId = 'copyId';
    this.paramSubscription = this.route.params.subscribe(params => {
      console.log(this.id = +params[copyId]);
      this.initForm();
    });
  }

  private initForm(): void {
    console.log('init form while editMode=' + this.service.isEdit);
    if (this.service.isEdit) {
      this.service.selected = this.id;
      this.service.fetchById(this.id).subscribe(gc => {
        this.gc = gc;
        this.form.setValue({
          objectCode: this.gc.objectCode,
          availableForLoan: this.gc.availableForLoan,
          generalState: this.gc.generalState,
          location: this.gc.location,
          wearCondition: this.gc.wearCondition,
          publisher: {
            name: this.gc.publisher.name
          }
        });
      });
    } else {
      this.service.selected = -1;
      this.form.reset();
    }
  }

  ngOnDestroy(): void {
    this.service.selected = null;
    this.paramSubscription.unsubscribe();
  }


  onLog(): void {
    console.log(this.gc);
  }
}
