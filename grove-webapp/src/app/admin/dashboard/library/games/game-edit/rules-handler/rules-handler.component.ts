import {Component} from '@angular/core';
import {GameService} from '../../game.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, Validators} from '@angular/forms';
import {Game} from '../../../../../../model/game.model';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-rules-handler',
  templateUrl: './rules-handler.component.html',
  styleUrls: ['./rules-handler.component.css']
})
export class RulesHandlerComponent {

  game: Game = this.service.getDetailedGame();
  form = this.initForm();

  constructor(private service: GameService,
              private route: ActivatedRoute,
              private router: Router,
              private fb: FormBuilder) {
  }

  onSubmit(): void {
    this.game.rules = this.form.value.rules;
    this.service.editGame(this.game.id, this.game)
      .pipe(map(game => this.game = game)).subscribe(() => {
      this.form = this.initForm();
      this.service.updateDetailedGame(this.game);
    });
    this.router.navigate(['./..'], {relativeTo: this.route});

  }

  onCancel(): void {
    this.form.patchValue({rules: this.game.rules});
  }

  onBack(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }

  initForm(): any {
    return this.fb.group({
      rules: [this.game.rules, Validators.maxLength(50000)]
    });
  }
}
