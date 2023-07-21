import {Component, OnInit} from '@angular/core';
import {Publisher} from '../../../model/publisher.model';
import {of, Subscription} from 'rxjs';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {DeletionHandlerService} from '../../../shared/services/deletion-handler.service';
import {Page} from '../../../model/page.model';
import {ModelEnum} from '../../../model/enum/model.enum';
import {concatMap} from 'rxjs/operators';
import {PublisherDataService} from '../publisher-data.service';
import {PublisherService} from '../publisher.service';

@Component({
  selector: 'app-publisher-detail',
  templateUrl: './publisher-detail.component.html',
  styleUrls: ['./publisher-detail.component.css']
})
export class PublisherDetailComponent implements OnInit {
  publisher: Publisher;
  private paramId: number;
  private subscription: Subscription;

  constructor(private publisherDataService: PublisherDataService,
              private publisherService: PublisherService,
              private route: ActivatedRoute,
              private router: Router,
              private deletionHandlerService: DeletionHandlerService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      const id = 'id';
      this.paramId = +params[id];
      this.publisher = this.publisherService.getPublisherById(+this.paramId);
    });
    this.subscription = this.publisherService.pagedPublishersChanged.subscribe((pagedPublishers: Page<Publisher>) => {
      this.publisher = pagedPublishers.content.find(publisher => publisher.id === this.paramId);
    });
  }

  onEdit(): void {
    this.router.navigate(['/admin/editor/publishers/', this.publisher.id, 'edit']);
  }

  onOpenConfirm(isPublisherDeletion: boolean): void {
    let chosenEnum = ModelEnum.PUBLISHER;
    if (!isPublisherDeletion) {
      chosenEnum = ModelEnum.CONTACT;
    }

    this.deletionHandlerService.callModal(chosenEnum, this.publisher, false)
      .then(value => {
        if (value === 'Ok click') {
          this.onDelete(isPublisherDeletion);
        }
      })
      .catch(err => console.log(err));
  }

  onDelete(isPublisherDeletion: boolean): void {
    if (isPublisherDeletion) {

      const observable = of(this.publisher.id);
      observable.pipe(
        concatMap(id => {
          return this.publisherDataService.removePublisher(id);
        }),
        concatMap(() => {
          return this.publisherDataService.fetchPublishers();
        })
      ).subscribe();
    } else {

      const observable = of(this.publisher.contact.id);
      observable.pipe(
        concatMap(id => {
          return this.publisherDataService.removeContact(this.publisher.id, id);
        }),
        concatMap(() => {
          return this.publisherDataService.fetchPublishers();
        })
      ).subscribe();
    }
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
