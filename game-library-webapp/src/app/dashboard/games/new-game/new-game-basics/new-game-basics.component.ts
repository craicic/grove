import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Game} from '../../../../model/game.model';
import {GameService} from '../../game.service';
import {GameNatureEnum} from '../../../../model/enum/game-nature.enum';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-new-game',
  templateUrl: './new-game-basics.component.html',
  styleUrls: ['./new-game-basics.component.css']
})
export class NewGameBasicsComponent implements OnInit {

  form: FormGroup;

  @Input()
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

  constructor(private service: GameService) {
    this.natureList = Object.keys(GameNatureEnum);
    this.actualEnumType = GameNatureEnum;
    this.playerQuickDisplay = '';
    this.ageRangeQuickDisplay = '';
  }

  ngOnInit(): void {
    this.initForm(this.game);
  }

  initForm(currentGame?: Game): void {
    this.form = new FormGroup({
      'title': new FormControl(currentGame ? currentGame.title : ''),
      'ageRange': new FormGroup({
        'min': new FormControl(currentGame ? currentGame.minAge : ''),
        'month': new FormControl(currentGame ? currentGame.minMonth : ''),
        'max': new FormControl(currentGame ? currentGame.maxAge : ''),
      }),
      'duration': new FormControl(currentGame ? currentGame.playTime : ''),
      'numberOfPlayers': new FormGroup({
        'min': new FormControl(currentGame ? currentGame.minNumberOfPlayer : ''),
        'max': new FormControl(currentGame ? currentGame.maxNumberOfPlayer : '')
      }),
      'gameNature': new FormControl(currentGame ? currentGame.nature : GameNatureEnum.BOARD_GAME),
      'description': new FormControl(currentGame ? currentGame.description : '')
    });
  }

  onSubmit(): void {
    console.log(this.form.value);
  }

  onNextPage(): void {
    console.log(this.form.value);

  }
}
