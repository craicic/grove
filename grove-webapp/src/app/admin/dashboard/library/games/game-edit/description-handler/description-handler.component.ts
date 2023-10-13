import {Component, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {Game} from '../../../../../../model/game.model';
import {GameService} from '../../game.service';
import {ActivatedRoute, Router} from '@angular/router';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-description-handler',
  templateUrl: './description-handler.component.html',
  styleUrls: ['./description-handler.component.css']
})
export class DescriptionHandlerComponent implements OnInit {

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
      'description': new UntypedFormControl(this.game.description, [
          Validators.maxLength(2000),
        ], []
      )
    });
  }

  onSubmit(): void {
    this.game.description = this.form.get('description').value;
    this.service.editGame(this.game.id, this.game)
      .pipe(map(game => this.game = game)).subscribe(() => {
      this.initForm();
      this.service.updateDetailedGame(this.game);
    });
    this.router.navigate(['./..'], {relativeTo: this.route});
  }


  onCancel(): void {
    this.form.patchValue({description: this.game.description});
  }

  onBack(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
