import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {CreatorService} from '../creator.service';
import {CreatorDataService} from '../creator-data.service';
import {Creator} from '../../../model/creator.model';
import {of, Subscription} from 'rxjs';
import {concatMap} from 'rxjs/operators';
import {DeletionHandlerService} from '../../../shared/services/deletion-handler.service';
import {ModelEnum} from '../../../model/enum/model.enum';
import {Page} from '../../../model/page.model';

@Component({
  selector: 'app-creator-detail',
  templateUrl: './creator-detail.component.html',
  styleUrls: ['./creator-detail.component.css']
})
export class CreatorDetailComponent implements OnInit {
  creator: Creator;
  private paramId: number;
  private subscription: Subscription;

  constructor(private creatorsDataService: CreatorDataService,
              private creatorsService: CreatorService,
              private route: ActivatedRoute,
              private router: Router,
              private deletionHandlerService: DeletionHandlerService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      const id = 'id';
      this.paramId = +params[id];
      this.creator = this.creatorsService.getCreatorById(+this.paramId);
    });
    this.subscription = this.creatorsService.pagedCreatorsChanged.subscribe((pagedCreators: Page<Creator>) => {
      this.creator = pagedCreators.content.find(creator => creator.id === this.paramId);
    });
  }

  onEdit(): void {
    this.router.navigate(['/admin/editor/creators/', this.creator.id, 'edit']);
  }

  onOpenConfirm(isCreatorDeletion: boolean): void {
    let chosenEnum = ModelEnum.CREATOR;
    if (!isCreatorDeletion) {
      chosenEnum = ModelEnum.CONTACT;
    }

    this.deletionHandlerService.callModal(chosenEnum, this.creator, true)
      .then(value => {
        if (value === 'Ok click') {
          this.onDelete(isCreatorDeletion);
        }
      })
      .catch(err => console.log(err));
  }

  onDelete(isCreatorDeletion: boolean): void {
    if (isCreatorDeletion) {

      const myObs = of(this.creator.id);
      myObs.pipe(
        concatMap(id => {
          return this.creatorsDataService.removeCreator(id);
        }),
        concatMap(() => {
          return this.creatorsDataService.fetchCreators();
        })
      ).subscribe();
    } else {

      const myObs = of(this.creator.contact.id);
      myObs.pipe(
        concatMap(id => {
          return this.creatorsDataService.removeContact(this.creator.id, id);
        }),
        concatMap(() => {
          return this.creatorsDataService.fetchCreators();
        })
      ).subscribe();
    }
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
