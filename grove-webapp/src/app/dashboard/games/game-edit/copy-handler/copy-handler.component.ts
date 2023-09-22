import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {GameCopy} from '../../../../model/game-copy.model';
import {GeneralStateEnum} from '../../../../model/enum/general-state.enum';
import {GameCopiesService} from '../../../game-copies/game-copies.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {map} from 'rxjs/operators';
import {GameService} from '../../game.service';

@Component({
  selector: 'app-copy-handler',
  templateUrl: './copy-handler.component.html',
  styleUrls: ['./copy-handler.component.css']
})
export class CopyHandlerComponent implements OnInit, OnDestroy {

  gc: GameCopy = new GameCopy();
  stateEnum: typeof GeneralStateEnum = GeneralStateEnum;
  stateList: Array<string> = Object.keys(GeneralStateEnum);
  private id: any;
  private paramSubscription: Subscription;

  form = this.fb.group({
    objectCode: ['', Validators.required],
    generalState: ['IN_ACTIVITY' as GeneralStateEnum, Validators.required],
    wearCondition: ['Neuf', Validators.required],
    location: [''],
    availableForLoan: [true]
  });

  constructor(private fb: FormBuilder,
              public service: GameCopiesService,
              private gameService: GameService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const copyId = 'copyId';
    this.paramSubscription = this.route.params.subscribe(params => {
      console.log(this.id = +params[copyId]);
      this.initForm();
    });
  }

  private initForm(): void {
    if (this.service.isEdit) {
      this.service.fetchById(this.id).subscribe(gc => {
        console.log(gc);
        this.gc = gc;
        this.service.copy = gc;
        this.form.setValue({
          objectCode: this.gc.objectCode,
          availableForLoan: this.gc.availableForLoan,
          generalState: this.gc.generalState,
          location: this.gc.location,
          wearCondition: this.gc.wearCondition,
        });
      });
    } else {
      this.form.setValue({
        objectCode: '',
        availableForLoan: true,
        generalState: 'IN_ACTIVITY' as GeneralStateEnum,
        location: '',
        wearCondition: 'Neuf',
      });
    }
  }

  ngOnDestroy(): void {
    this.paramSubscription.unsubscribe();
  }


  onLog(): void {
    console.log(this.gc);
  }

  onSubmit(): void {
    this.gc.objectCode = this.form.value.objectCode;
    this.gc.wearCondition = this.form.value.wearCondition;
    this.gc.location = this.form.value. location;
    this.gc.generalState = this.form.value.generalState;
    this.gc.availableForLoan = this.form.value.availableForLoan;
    this.gc.gameId = this.gameService.game.id;
    if (this.service.isEdit) {
      this.service.editCopy(this.gc.id, this.gc)
        .pipe(map((copy: GameCopy) => this.service.copy = copy)).subscribe();
    } else {
      this.service.saveCopy(this.gc)
        .pipe(map((copy: GameCopy) => this.service.copy = copy)).subscribe();
    }
  }
}
