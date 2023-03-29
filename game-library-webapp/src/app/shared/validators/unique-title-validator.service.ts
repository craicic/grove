import {AbstractControl, AsyncValidator, ValidationErrors} from '@angular/forms';
import {Injectable} from '@angular/core';
import {GameService} from '../../dashboard/games/game.service';
import {Observable, of} from 'rxjs';

@Injectable({providedIn: 'root'})
export class UniqueTitleValidator implements AsyncValidator {
  takenNames: string[];
  isTaken: boolean;


  constructor(private gameService: GameService) {
    this.updateTakenNames();
  }

  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    if (this.takenNames) {
      this.isTaken = this.takenNames.includes(control.value.toLowerCase().trim());
    }
    return of(this.isTaken ? {uniqueTitle: true} : null);
  }

  updateTakenNames(): void {
    this.isTaken = true;
    this.gameService.fetchNames()
      .subscribe(names => this.takenNames = names);
  }
}
