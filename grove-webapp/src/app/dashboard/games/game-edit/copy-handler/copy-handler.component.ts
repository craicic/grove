import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {GameCopy} from '../../../../model/game-copy.model';
import {GeneralStateEnum} from '../../../../model/enum/general-state.enum';
import {GameCopiesService} from '../../../game-copies/game-copies.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {GameService} from '../../game.service';
import {Game} from '../../../../model/game.model';

@Component({
  selector: 'app-copy-handler',
  templateUrl: './copy-handler.component.html',
  styleUrls: ['./copy-handler.component.css']
})
export class CopyHandlerComponent implements OnInit, OnDestroy {

  copy: GameCopy = new GameCopy();
  stateEnum: typeof GeneralStateEnum = GeneralStateEnum;
  stateList: Array<string> = Object.keys(GeneralStateEnum);
  private id: any;
  private game: Game;
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
    if (this.service.isEdit) {
      this.service.fetchById(this.id).subscribe(c => {
        console.log(c);
        this.copy = c;
        this.fillForm();
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


  onBack(): void {
    console.log(this.copy);
  }

  onSubmit(): void {
    this.copy.objectCode = this.form.value.objectCode;
    this.copy.wearCondition = this.form.value.wearCondition;
    this.copy.location = this.form.value.location;
    this.copy.generalState = this.form.value.generalState;
    this.copy.availableForLoan = this.form.value.availableForLoan;
    this.game = this.gameService.getDetailedGame();
    this.copy.gameId = this.game.id;
    if (this.service.isEdit) {
      this.service.edit(this.copy.id, this.copy)
        .subscribe((c: GameCopy) => {
          const idx = this.game.copies.findIndex(item => item.id === c.id);
          this.game.copies[idx] = c;
          this.gameService.updateDetailedGame(this.game);
        });
    } else {
      this.service.save(this.copy)
        .subscribe((c: GameCopy) => {
          this.game.copies.push(c);
          this.gameService.updateDetailedGame(this.game);
          this.service.isEdit = true;
          this.router.navigate(['..', c.id], {relativeTo: this.route});
        });
    }
  }

  onCancel(): void {
    this.fillForm();
  }

  onDelete(): void {
    this.game = this.gameService.getDetailedGame();
    this.service.delete(this.copy)
      .subscribe(() => {
          const idx = this.game.copies.findIndex(item => item.id === this.copy.id);
          this.game.copies.splice(idx, 1);
          this.gameService.updateDetailedGame(this.game);
          this.copy = null;
          this.service.copy = null;
          this.router.navigate(['admin/locked-mode/games', this.game.id, 'edit']);
        }
      );
  }

  fillForm(): void {
    this.form.setValue({
      objectCode: this.copy.objectCode,
      availableForLoan: this.copy.availableForLoan,
      generalState: this.copy.generalState,
      location: this.copy.location,
      wearCondition: this.copy.wearCondition
    });
  }
}
