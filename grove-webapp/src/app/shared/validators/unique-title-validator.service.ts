import {AbstractControl, AsyncValidator, ValidationErrors} from '@angular/forms';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {GameService} from '../../admin/dashboard/library/games/game.service';

@Injectable({providedIn: 'root'})
export class UniqueTitleValidator implements AsyncValidator {
  takenTitles: string[];
  isTaken: boolean;


  constructor(private gameService: GameService) {
    this.updateTakenTitles();
  }

  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    if (this.takenTitles) {
      this.isTaken = this.takenTitles.includes(control.value.toLowerCase().trim());
    }
    return of(this.isTaken ? {uniqueTitle: true} : null);
  }

  updateTakenTitles(): void {
    this.isTaken = true;
    this.gameService.fetchTitles()
      .subscribe(title => this.takenTitles = title);
  }
}
