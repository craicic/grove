import {Component, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {Game} from '../../../../model/game.model';
import {GameService} from '../../game.service';
import {ActivatedRoute, Router} from '@angular/router';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-size-handler',
  templateUrl: './size-handler.component.html',
  styleUrls: ['./size-handler.component.css']
})
export class SizeHandlerComponent implements OnInit {

  form: UntypedFormGroup;
  game: Game;

  constructor(private service: GameService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.game = this.service.getDetailedGame();
    this.initForm();
  }

  private initForm(): void {
    this.form = new UntypedFormGroup({
      'gameSize': new UntypedFormControl(this.game.size, [
          Validators.maxLength(100)
        ]
      )
    });
  }

  onSubmit(): void {
    this.game.size = this.form.get('gameSize').value;
    this.service.editGame(this.game.id, this.game)
      .pipe(map(game => this.game = game)).subscribe(() => {
      this.initForm();
      this.service.updateDetailedGame(this.game);
    });
    this.router.navigate(['./..'], {relativeTo: this.route});
  }


  onCancel(): void {
    this.form.patchValue({'gameSize': this.game.size});
  }

  onBack(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
