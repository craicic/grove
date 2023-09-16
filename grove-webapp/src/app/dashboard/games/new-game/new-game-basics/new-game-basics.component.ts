import {Component, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {Game} from '../../../../model/game.model';
import {GameService} from '../../game.service';
import {GameNatureEnum} from '../../../../model/enum/game-nature.enum';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {GameInfoHelper} from '../../../shared/refactoring/game-info-helper';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-new-game',
  templateUrl: './new-game-basics.component.html',
  styleUrls: ['./new-game-basics.component.css']
})
export class NewGameBasicsComponent implements OnInit {

  form: UntypedFormGroup;
  game: Game = new Game();
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
              private helper: GameInfoHelper,
              private route: ActivatedRoute,
              private router: Router) {
    this.natureList = Object.keys(GameNatureEnum);
    this.actualEnumType = GameNatureEnum;
    this.playerQuickDisplay = '';
    this.ageRangeQuickDisplay = '';
  }

  ngOnInit(): void {
    this.hasMaxP = false;
    this.hasMaxA = false;
    this.timeUnit = 'ans';
    this.timeUnitSwitch = 'Âge en mois ?';
    this.ageInYear = true;

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

  initForm(): void {
    this.form = new UntypedFormGroup({
      'title': new UntypedFormControl('', [Validators.maxLength(255), Validators.required]),
      'ageRange': new UntypedFormGroup({
        'min': new UntypedFormControl(12, [Validators.min(0), Validators.required]),
        'month': new UntypedFormControl(0, [Validators.min(0), Validators.required]),
        'max': new UntypedFormControl(0, [Validators.min(0), Validators.required]),
      }, [this.helper.ageRangeValidator.bind(this)]),
      'duration': new UntypedFormControl('', [Validators.maxLength(20)]),
      'numberOfPlayers': new UntypedFormGroup({
        'min': new UntypedFormControl(1, [Validators.min(1), Validators.required]),
        'max': new UntypedFormControl(0, [Validators.min(0), Validators.required])
      }, [this.helper.playerRangeValidator.bind(this)]),
      'gameNature': new UntypedFormControl(this.natureList[4], [Validators.required]),
      'description': new UntypedFormControl('')
    });
  }

  onSubmit(): void {
    this.game.title = this.form.get('title').value;
    this.game.nature = this.form.get('gameNature').value;
    this.game.minNumberOfPlayer = this.form.get('numberOfPlayers.min').value;
    this.game.maxNumberOfPlayer = this.form.get('numberOfPlayers.max').value;
    this.game.playTime = this.form.get('duration').value;
    this.game.minMonth = this.form.get('ageRange.month').value;
    this.game.minAge = this.form.get('ageRange.min').value;
    this.game.maxAge = this.form.get('ageRange.max').value;

    this.service.saveGame(this.game)
      .pipe(map(game => this.game = game))
      .subscribe(() => {
          this.service.updateDetailedGame(this.game);
          this.router.navigate(['./admin/locked-mode/games/' + this.game.id + '/edit']);
        }
      );
  }


  onCancel(): void {
  }

  onBack(): void {
    this.router.navigate(['/admin/editor/games']);
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
