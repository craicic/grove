import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, Subscription} from 'rxjs';
import {Game} from '../../../../model/game.model';
import {GameService} from '../../game.service';
import {MechanismService} from '../../../mechanism/mechanism.service';
import {Mechanism} from '../../../../model/mechansim.model';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-mechanism-handler',
  templateUrl: './mechanism-handler.component.html',
  styleUrls: ['./mechanism-handler.component.css']
})
export class MechanismHandlerComponent implements OnInit, OnDestroy {
  allMechanisms$: Observable<Mechanism[]>;
  gameMechanisms$: BehaviorSubject<Mechanism[]> = new BehaviorSubject<Mechanism[]>(null);
  addModeOn: boolean;
  private game: Game;
  subscription: Subscription;

  constructor(private service: GameService,
              private mechanismService: MechanismService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.addModeOn = false;
    this.subscription = this.service.detailedGame$.subscribe(game => {
      this.gameMechanisms$.next(game.mechanisms);
      this.game = game;
    });
    this.allMechanisms$ = this.mechanismService.fetchAll();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onAddMode(): void {
    this.addModeOn = true;
  }

  attachMechanism($mechanism: Mechanism): void {
    this.addModeOn = false;
    if ($mechanism) {
      this.service.addMechanism(this.game.id, $mechanism.id).subscribe(game => this.service.detailedGame$.next(game));
    }
  }

  onRemove(mechanismId: number): void {
    this.service.unlinkMechanism(this.game.id, mechanismId).subscribe(game => this.service.detailedGame$.next(game));
  }

  onBack(): void {
    this.router.navigate(['./..'], {relativeTo: this.route});
  }
}
