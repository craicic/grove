import {Component, OnDestroy, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {GameService} from '../../game.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Game} from '../../../../model/game.model';
import {GameNatureEnum} from '../../../../model/enum/game-nature.enum';
import {map} from 'rxjs/operators';
import {Subscription} from 'rxjs';
import {GameInfoHelper} from '../../../shared/refactoring/game-info-helper';

@Component({
  selector: 'app-info-handler',
  templateUrl: './info-handler.component.html',
  styleUrls: ['./info-handler.component.css']
})
export class InfoHandlerComponent implements OnInit, OnDestroy {
  form: UntypedFormGroup;
  game: Game;
  natureList: Array<string>;
  actualEnumType: typeof GameNatureEnum;

  hasMaxP: boolean;
  hasMaxA: boolean;
  timeUnit: string;
  timeUnitSwitch: string;
  ageInYear: boolean;

  playerQuickDisplay: string;
  ageRangeQuickDisplay: string;
  playerSubscription: Subscription;
  ageRangeSubscription: Subscription;

  constructor(protected service: GameService,
              private helper: GameInfoHelper,
              private route: ActivatedRoute,
              private router: Router) {
    this.natureList = Object.keys(GameNatureEnum);
    this.actualEnumType = GameNatureEnum;
    this.playerQuickDisplay = '';
    this.ageRangeQuickDisplay = '';

  }

  ngOnInit(): void {
    this.game = this.service.getDetailedGame();
    this.hasMaxP = this.game.maxNumberOfPlayer > 1;
    this.hasMaxA = this.game.maxAge > 1;

    if (this.game.minMonth > 0) {
      this.timeUnit = 'mois';
      this.timeUnitSwitch = 'Âge en années ?';
      this.ageInYear = false;
    } else {
      this.timeUnit = 'ans';
      this.timeUnitSwitch = 'Âge en mois ?';
      this.ageInYear = true;
    }

    this.initForm();
    this.playerQuickDisplay = this.helper
      .buildPlayers(this.form.get('numberOfPlayers.min').value, this.form.get('numberOfPlayers.max').value);
    this.ageRangeQuickDisplay =
      this.helper.buildAge(
        this.form.get('ageRange.min').value,
        this.form.get('ageRange.max').value,
        this.form.get('ageRange.month').value);

    this.playerSubscription = this.form.get('numberOfPlayers').valueChanges.subscribe(data => {
      this.playerQuickDisplay = this.helper.buildPlayers(data.min, data.max);
    });

    this.ageRangeSubscription = this.form.get('ageRange').valueChanges.subscribe(data => {
      this.ageRangeQuickDisplay = this.helper.buildAge(data.min, data.max, data.month);
    });
  }

  ngOnDestroy(): void {
    this.playerSubscription.unsubscribe();
    this.ageRangeSubscription.unsubscribe();
  }

  private initForm(): void {
    this.form = new UntypedFormGroup({
      'gameNature': new UntypedFormControl(this.game.nature, [Validators.required]),
      'numberOfPlayers': new UntypedFormGroup({
        'min': new UntypedFormControl(this.game.minNumberOfPlayer, [Validators.min(1), Validators.required]),
        'max': new UntypedFormControl(this.game.maxNumberOfPlayer, [Validators.min(0), Validators.required]),
      }, [this.helper.playerRangeValidator.bind(this)]),
      'duration': new UntypedFormControl(this.game.playTime, [Validators.maxLength(20)]),
      'ageRange': new UntypedFormGroup({
        'month': new UntypedFormControl(this.game.minMonth, [Validators.min(0), Validators.required]),
        'min': new UntypedFormControl(this.game.minAge, [Validators.min(0), Validators.required]),
        'max': new UntypedFormControl(this.game.maxAge, [Validators.min(0), Validators.required])
      }, [this.helper.ageRangeValidator.bind(this)]),
    });
  }

  onSubmit(): void {
    this.game.nature = this.form.get('gameNature').value;
    this.game.minNumberOfPlayer = this.form.get('numberOfPlayers.min').value;
    this.game.maxNumberOfPlayer = this.form.get('numberOfPlayers.max').value;
    this.game.playTime = this.form.get('duration').value;
    this.game.minMonth = this.form.get('ageRange.month').value;
    this.game.minAge = this.form.get('ageRange.min').value;
    this.game.maxAge = this.form.get('ageRange.max').value;

    this.service.editGame(this.game.id, this.game)
      .pipe(map(game => this.game = game)).subscribe(() => {
      this.service.updateDetailedGame(this.game);
    });
    this.router.navigate(['./..'], {relativeTo: this.route});
  }

  onCancel(): void {
    this.form.patchValue(
      {
        gameNature: this.game.nature,
        numberOfPlayers: {
          min: this.game.minNumberOfPlayer,
          max: this.game.maxNumberOfPlayer
        },
        duration: this.game.playTime,
        ageRange: {
          min: this.game.minAge,
          month: this.game.minMonth,
          max: this.game.maxAge
        }
      });
  }

  onBack(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }


  onRemoveMaxP(): void {
    this.hasMaxP = false;
    this.form.patchValue({numberOfPlayers: {max: 0}});
  }

  onAddMaxP(): void {
    this.hasMaxP = true;
  }

  onRemoveMaxA(): void {
    this.hasMaxA = false;
    this.form.patchValue({ageRange: {max: 0}});
  }

  onAddMaxA(): void {
    this.hasMaxA = true;
  }

  onSwitchToMonth(): void {
    this.ageInYear = false;
    this.timeUnit = 'mois';

    this.form.patchValue({ageRange: {month: this.form.get('ageRange.min').value}});
    this.form.patchValue({ageRange: {min: 0}});
  }

  onSwitchToYear(): void {
    this.ageInYear = true;
    this.timeUnit = 'ans';

    this.form.patchValue({ageRange: {min: this.form.get('ageRange.month').value}});
    this.form.patchValue({ageRange: {month: 0}});
  }
}
