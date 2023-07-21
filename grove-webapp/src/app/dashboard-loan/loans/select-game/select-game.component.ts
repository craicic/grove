import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {GameCopy} from '../../../model/game-copy.model';
import {LoanService} from '../loan.service';
import {ActivatedRoute, Router} from '@angular/router';
import {map, tap} from 'rxjs/operators';

@Component({
  selector: 'app-select-game',
  templateUrl: './select-game.component.html',
  styleUrls: ['./select-game.component.css']
})
export class SelectGameComponent implements OnInit {

  copies$: Observable<GameCopy[]>;

  constructor(private service: LoanService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.copies$ = this.service.fetchAvailableCopies();
  }

  onSelectGameCopy(copyId: number): void {
    // We store the gameCopy in service.
    this.copies$.pipe(
      map(copies => copies.find(copy => copy.id === copyId)),
      tap(x => console.log(x))).subscribe(value => {
      this.service.copy = value;
      this.router.navigate(['admin/loans/confirm']);
    });


  }

  onNavigateToDetail(id: number): void {
  }
}
