import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {GameCopy} from '../../../../model/game-copy.model';
import {Publisher} from '../../../../model/publisher.model';
import {GeneralStateEnum} from '../../../../model/enum/general-state.enum';
import {GameCopiesService} from '../../../game-copies/game-copies.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-copy-handler',
  templateUrl: './copy-handler.component.html',
  styleUrls: ['./copy-handler.component.css']
})
export class CopyHandlerComponent implements OnInit, OnDestroy {

  gc: GameCopy;
  pb: Publisher;
  actualEnumType: typeof GeneralStateEnum = GeneralStateEnum;
  stateList: Array<string> = Object.keys(GeneralStateEnum);
  private param: any;
  private subscription: Subscription;

  form = this.fb.group({
    objectCode: ['', Validators.required],
    generalState: [this.stateList[0], Validators.required],
    wearCondition: ['Neuf', Validators.required],
    location: [''],
    availableForLoan: [true],
    publisher: this.fb.group({
      name: ['']
    })
  });

  constructor(private fb: FormBuilder,
              private service: GameCopiesService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    if (!this.router.url.endsWith('new')) {
      this.subscription = this.route.params.subscribe(params => {
        console.log(this.param = params['id']);
      });


      this.service.fetchById(+this.param).subscribe(gc => this.gc = gc);
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onLog(): void {
    console.log(this.gc);
  }
}
