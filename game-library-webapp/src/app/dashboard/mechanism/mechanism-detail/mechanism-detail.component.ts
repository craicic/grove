import {Component, OnDestroy, OnInit} from '@angular/core';
import {Mechanism} from '../../../model/mechansim.model';
import {MechanismService} from '../mechanism.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {of, Subscription} from 'rxjs';
import {MechanismDataService} from '../mechanism-data.service';
import {Page} from '../../../model/page.model';
import {concatMap} from 'rxjs/operators';
import {DeletionHandlerService} from '../../../shared/services/deletion-handler.service';
import {ModelEnum} from '../../../model/enum/model.enum';

@Component({
  selector: 'app-mechanism-detail',
  templateUrl: './mechanism-detail.component.html',
  styleUrls: ['./mechanism-detail.component.css']
})
export class MechanismDetailComponent implements OnInit, OnDestroy {
  mechanism: Mechanism;
  private paramId: number;
  private subscription: Subscription;

  constructor(private mechanismService: MechanismService,
              private mechanismDataService: MechanismDataService,
              private route: ActivatedRoute,
              private router: Router,
              private deletionHandlerService: DeletionHandlerService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      const id = 'id';
      this.paramId = +params[id];
      this.mechanism = this.mechanismService.getMechanismById(+this.paramId);
    });
    this.subscription = this.mechanismService.pagedMechanismsChanged.subscribe((pagedMechanism: Page<Mechanism>) => {
      this.mechanism = pagedMechanism.content.find(mechanism => mechanism.id === this.paramId);
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onEdit(): void {
    this.router.navigate(['/admin/editor/mechanisms/', this.mechanism.id, 'edit']);
  }

  onDelete(): void {
    const myObs = of(this.mechanism.id);
    myObs.pipe(
      concatMap(id => {
        return this.mechanismDataService.removeMechanism(id);
      }),
      concatMap(() => {
        return this.mechanismDataService.fetchMechanisms();
      })
    ).subscribe();
    this.router.navigate(['../'], {relativeTo: this.route});
  }

  onOpenConfirm(): void {
    this.deletionHandlerService.callModal(ModelEnum.THEME, this.mechanism, false)
      .then(value => {
        if (value === 'Ok click') {
          this.onDelete();
        }
      })
      .catch(err => console.log(err));
  }
}
