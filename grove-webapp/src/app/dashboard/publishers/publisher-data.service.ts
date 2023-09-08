import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigurationService} from '../configuration/configuration.service';
import {environment} from '../../../environments/environment';
import {Page} from '../../model/page.model';
import {Publisher} from '../../model/publisher.model';
import {tap} from 'rxjs/operators';
import {PublisherService} from './publisher.service';
import {ImpersonalInterface} from '../../model/interface/impersonal.interface';

@Injectable({providedIn: 'root'})
export class PublisherDataService {
  apiUri: string;

  constructor(private publishersService: PublisherService,
              private http: HttpClient,
              private configurationService: ConfigurationService) {

    this.apiUri = environment.apiUri;
  }

  fetchNames(): any {
    return this.http
      .get<ImpersonalInterface[]>(this.apiUri + '/api/admin/publishers/names', {responseType: 'json'})
      .pipe(
        tap(names => {
          this.publishersService.setNames(names);
        })
      );
  }

  fetchPublishers(page?: number, keyword?: string): any {
    if (!page) {
      page = 0;
    }
    let keywordParam = '';
    if (keyword) {
      keywordParam = '&search=' + keyword.toLowerCase();
    }
    const size = this.configurationService.getNumberOfElements();
    const params = '?page=' + page + '&size=' + size + '&sort=id' + keywordParam;

    return this.http
      .get<Page<Publisher>>(this.apiUri + '/api/admin/publishers/page' + params, {responseType: 'json'})
      .pipe(
        tap(pagedPublishers => {
          this.publishersService.setPagedPublishers(pagedPublishers);
        })
      );
  }

  removePublisher(id: number): any {
    return this.http
      .delete(this.apiUri + '/api/admin/publishers/' + id);
  }

  removeContact(publisherId: number, contactId): any {
    return this.http
      .delete(this.apiUri + '/api/admin/publishers/' + publisherId + '/contact/' + contactId);
  }

  editPublisher(id: number, editedPublisher: Publisher): void {
    this.http
      .put<Publisher>(this.apiUri + '/api/admin/publishers/' + id, editedPublisher, {responseType: 'json'})
      .subscribe(publisher => this.publishersService.updatePublishers(publisher));
  }

  addPublisher(newPublisher: Publisher, hasContact: boolean): void {
    let contactParam = '';
    if (hasContact) {
      contactParam = '?has-contact=true';
    }
    this.http
      .post<Publisher>(this.apiUri + '/api/admin/publishers' + contactParam, newPublisher, {responseType: 'json'})
      .subscribe(publisher => this.publishersService.updatePublishers(publisher));
  }
}
