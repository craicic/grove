import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MechanismService} from './mechanism.service';
import {environment} from '../../../environments/environment';
import {tap} from 'rxjs/operators';
import {Mechanism} from '../../model/mechansim.model';
import {ConfigurationService} from '../configuration/configuration.service';
import {Observable} from 'rxjs';
import {Page} from '../../model/page.model';

@Injectable({providedIn: 'root'})
export class MechanismDataService {
  private readonly apiUri: string;

  constructor(private http: HttpClient,
              private mechanismsService: MechanismService,
              private configurationService: ConfigurationService) {

    this.apiUri = environment.apiUri;
  }

  fetchNames(): Observable<Mechanism[]> {
    return this.http
      .get<Mechanism[]>(this.apiUri + '/admin/mechanisms', {responseType: 'json'})
      .pipe(
        tap(mechanisms => {
          this.mechanismsService.setNames(mechanisms);
        })
      );
  }

  fetchMechanisms(page?: number, keyword?: string): Observable<Page<Mechanism>> {
    if (!page) {
      page = 0;
    }
    let keywordParam = '';
    if (keyword) {
      keywordParam = '&search=' + keyword.toLowerCase();
    }
    const size = this.configurationService.getNumberOfElements();
    const args = '?page=' + page + '&size=' + size + '&sort=title' + keywordParam;

    return this.http
      .get<Page<Mechanism>>(this.apiUri + '/admin/mechanisms/page' + args, {responseType: 'json'})
      .pipe(
        tap(pagedMechanisms => {
          this.mechanismsService.setPagedMechanisms(pagedMechanisms);
        })
      );
  }

  editMechanism(id: number, editedMechanism: Mechanism): any {
    return this.http
      .put<Mechanism>(this.apiUri + '/admin/mechanisms/' + id, editedMechanism, {responseType: 'json'});
  }

  addMechanism(newMechanism: Mechanism): any {
    return this.http
      .post<Mechanism>(this.apiUri + '/admin/mechanisms', newMechanism, {responseType: 'json'});
  }

  removeMechanism(id: number): any {
    return this.http.delete<Mechanism>(this.apiUri + '/admin/mechanisms/' + id);
  }
}
