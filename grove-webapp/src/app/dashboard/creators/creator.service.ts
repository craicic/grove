import {Injectable} from '@angular/core';
import {Creator} from '../../model/creator.model';
import {Page} from '../../model/page.model';
import {Observable, Subject} from 'rxjs';
import {Person} from '../../model/interface/person.interface';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CreatorService {
  readonly apiUri: string;
  pagedCreators: Page<Creator>;
  pagedCreatorsChanged: Subject<Page<Creator>> = new Subject<Page<Creator>>();
  existingNames: Person[];

  constructor(private http: HttpClient) {
    this.apiUri = environment.apiUri;
  }

  setPagedCreators(pagedCreators: Page<Creator>): void {
    this.pagedCreators = pagedCreators;
    this.pagedCreatorsChanged.next(this.pagedCreators);
  }

  getExistingNames(): Person[] {
    return this.existingNames;
  }

  getCreatorById(id: number): Creator {
    return this.getCreators().find(creator => creator.id === id);
  }

  private getCreators(): Creator[] {
    return this.pagedCreators.content.slice();
  }

  updateCreators(creator: Creator): void {
    this.pagedCreators.content = this.pagedCreators.content.filter(streamedCreator => creator.id !== streamedCreator.id);
    this.pagedCreators.content.push(creator);

    this.pagedCreatorsChanged.next(this.pagedCreators);
  }

  setNames(names: Person[]): void {
    names.forEach(value => {
      value.lastName = value.lastName.toLowerCase().trim();
      value.firstName = value.firstName.toLowerCase().trim();
    });
    console.table(names);
    this.existingNames = names.slice();
  }

  /* ============================================== REST API METHODS =================================================================== */
  fetchAllNames(): Observable<Creator[]> {
    return this.http.get<Creator[]>(this.apiUri + '/api/admin/creators/names', {responseType: 'json'});
  }

  findByFullName(fullName: string): Observable<Creator> {
    return this.http.get<Creator>(this.apiUri + '/api/admin/creators?full-name=' + fullName, {responseType: 'json'});
  }
}
