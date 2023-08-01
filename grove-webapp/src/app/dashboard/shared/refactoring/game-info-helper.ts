import {UntypedFormGroup, ValidatorFn} from '@angular/forms';
import {GameService} from '../../games/game.service';
import {Injectable} from '@angular/core';
import {IGNORE_WARNINGS} from '@angular-devkit/build-angular/src/webpack/utils/stats';

@Injectable({providedIn: 'root'})
export class GameInfoHelper {


  constructor(protected service: GameService) {
  }


  playerRangeValidator: ValidatorFn = (fg: UntypedFormGroup) => {
    const min = fg.get('min').value;
    const max = fg.get('max').value;
    return ((min >= 1 && min <= max) || max === 0) ? null : {playerRangeError: true};
  };

  ageRangeValidator: ValidatorFn = (fg: UntypedFormGroup) => {
    const month = fg.get('month').value;
    const min = fg.get('min').value;
    const max = fg.get('max').value;
    if ((min >= 0 && min < max && month < max * 12) || max === 0) {
      return null;
    }
    return {ageRangeError: true};
  };

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
