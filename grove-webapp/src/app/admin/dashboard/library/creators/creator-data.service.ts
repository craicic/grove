import {Injectable} from '@angular/core';
import {CreatorService} from './creator.service';
import {HttpClient} from '@angular/common/http';
import {ConfigurationService} from '../configuration/configuration.service';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {environment} from '../../../../../environments/environment';
import {Person} from '../../../../model/interface/person.interface';
import {Creator} from '../../../../model/creator.model';
import {Page} from '../../../../model/page.model';

@Injectable({
  providedIn: 'root'
})
export class CreatorDataService {
  apiUri: string;

  constructor(private creatorsService: CreatorService,
              private http: HttpClient,
              private configurationService: ConfigurationService) {

    this.apiUri = environment.apiUri;
  }

  fetchNames(): Observable<Person[]> {
    return this.http
      .get<Person[]>(this.apiUri + '/api/admin/creators/names', {responseType: 'json'})
      .pipe(
        tap(names => {
          this.creatorsService.setNames(names);
        })
      );
  }

  fetchCreators(page?: number, keyword?: string): any {
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
      .get<Page<Creator>>(this.apiUri + '/api/admin/creators/page' + params, {responseType: 'json'})
      .pipe(
        tap(pagedCreators => {
          this.creatorsService.setPagedCreators(pagedCreators);
        })
      );
  }

  removeCreator(id: number): any {
    return this.http
      .delete(this.apiUri + '/api/admin/creators/' + id);
  }

  removeContact(creatorId: number): any {
    return this.http
      .delete(this.apiUri + '/api/admin/creators/' + creatorId + '/contact');
  }

  editCreator(id: number, editedCreator: Creator): void {
    this.http
      .put<Creator>(this.apiUri + '/api/admin/creators/' + id, editedCreator, {responseType: 'json'})
      .subscribe(creator => this.creatorsService.updateCreators(creator));
  }

  addCreator(newCreator: Creator, hasContact: boolean): void {
    let contactParam = '';
    if (hasContact) {
      contactParam = '?has-contact=true';
    }
    this.http
      .post<Creator>(this.apiUri + '/api/admin/creators' + contactParam, newCreator, {responseType: 'json'})
      .subscribe(creator => this.creatorsService.updateCreators(creator));
  }
}
