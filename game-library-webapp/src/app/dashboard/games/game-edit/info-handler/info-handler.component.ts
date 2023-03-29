import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {GameService} from '../../game.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Game} from '../../../../model/game.model';
import {GameNatureEnum} from '../../../../model/enum/game-nature.enum';
import {map} from 'rxjs/operators';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-info-handler',
  templateUrl: './info-handler.component.html',
  styleUrls: ['./info-handler.component.css']
})
export class InfoHandlerComponent implements OnInit, OnDestroy {
  form: FormGroup;
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

  constructor(private service: GameService,
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
    this.playerQuickDisplay = this.buildPlayers(this.form.get('numberOfPlayers.min').value, this.form.get('numberOfPlayers.max').value);
    this.ageRangeQuickDisplay = this.buildAge(this.form.get('ageRange.min').value, this.form.get('ageRange.max').value, this.form.get('ageRange.month').value);

    this.playerSubscription = this.form.get('numberOfPlayers').valueChanges.subscribe(data => {
      this.playerQuickDisplay = this.buildPlayers(data.min, data.max);
    });

    this.ageRangeSubscription = this.form.get('ageRange').valueChanges.subscribe(data => {
      this.ageRangeQuickDisplay = this.buildAge(data.min, data.max, data.month);
    });
  }

  ngOnDestroy(): void {
    this.playerSubscription.unsubscribe();
    this.ageRangeSubscription.unsubscribe();
  }

  private initForm(): void {
    this.form = new FormGroup({
      'gameNature': new FormControl(this.game.nature, [Validators.required]),
      'numberOfPlayers': new FormGroup({
        'min': new FormControl(this.game.minNumberOfPlayer, [Validators.min(1), Validators.required]),
        'max': new FormControl(this.game.maxNumberOfPlayer, [Validators.min(0), Validators.required]),
      }, [this.playerRangeValidator.bind(this)]),
      'duration': new FormControl(this.game.playTime, [Validators.maxLength(20)]),
      'ageRange': new FormGroup({
        'month': new FormControl(this.game.minMonth, [Validators.min(0), Validators.required]),
        'min': new FormControl(this.game.minAge, [Validators.min(0), Validators.required]),
        'max': new FormControl(this.game.maxAge, [Validators.min(0), Validators.required])
      }, [this.ageRangeValidator.bind(this)]),
    });
    console.log(this.form);
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

  playerRangeValidator: ValidatorFn = (fg: FormGroup) => {
    const min = fg.get('min').value;
    const max = fg.get('max').value;
    return (min >= 1 && min <= max) || max === 0
      ? null
      : {playerRangeError: true};
  };

  ageRangeValidator: ValidatorFn = (fg: FormGroup) => {
    const month = fg.get('month').value;
    const min = fg.get('min').value;
    const max = fg.get('max').value;
    if ((min >= 0 && min < max && month < max * 12) || max === 0) {
      return null;
    }
    return {ageRangeError: true};
  };

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

  buildPlayers(min?: number, max?: number): string {
    if (min === null || max === null) {
      return '';
    }
    if (max >= min || max === 0) {
      return this.service.buildPLayers(min, max);
    }
    return '';
  }

  buildAge(minAge: number, maxAge: number, minMonth: number): string {
    if (minAge === null || maxAge === null || minMonth === null) {
      return '';
    }
    if (maxAge === 0 || (maxAge > minAge && maxAge * 12 > minMonth)) {
      return this.service.buildAge(minAge, maxAge, minMonth);
    }
    return '';
  }
}
