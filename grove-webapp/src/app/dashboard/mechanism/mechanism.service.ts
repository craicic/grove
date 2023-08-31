import {Injectable} from '@angular/core';
import {Mechanism} from '../../model/mechansim.model';
import {Observable, Subject} from 'rxjs';
import {Page} from '../../model/page.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable({providedIn: 'root'})
export class MechanismService {
  apiUri: string;
  pagedMechanisms: Page<Mechanism>;
  pagedMechanismsChanged = new Subject<Page<Mechanism>>();
  existingMechanisms: Mechanism[];

  constructor(private http: HttpClient) {
    this.apiUri = environment.apiUri;
  }

  /* ============================================== REST API METHODS =================================================================== */
  fetchAll(): Observable<Mechanism[]> {
    return this.http
      .get<Mechanism[]>(this.apiUri + '/api/admin/mechanisms', {responseType: 'json'});
  }

  /* ================================================ OTHER METHODS ==================================================================== */

  setNames(mechanism: Mechanism[]): void {
    this.existingMechanisms = mechanism.slice();
    console.table(this.existingMechanisms);
  }

  getExistingMechanisms(): string[] {
    const mechanismAsList: string[] = [];
    this.existingMechanisms.forEach((mechanism: Mechanism) =>
      mechanismAsList.push(mechanism.title.toLowerCase().trim()));
    return mechanismAsList;
  }


  setPagedMechanisms(pagedMechanisms: Page<Mechanism>): void {
    this.pagedMechanisms = pagedMechanisms;
    this.pagedMechanismsChanged.next(this.pagedMechanisms);
  }

  getMechanisms(): Mechanism[] {
    return this.pagedMechanisms.content.slice();
  }

  getMechanismById(givenId: number): Mechanism {
    return this.getMechanisms().find(mechanism => mechanism.id === givenId);
  }

  updateMechanisms(mechanism: Mechanism): void {
    this.pagedMechanisms.content = this.pagedMechanisms.content.filter(streamedMechanism => mechanism.id !== streamedMechanism.id);
    this.pagedMechanisms.content.push(mechanism);

    this.pagedMechanismsChanged.next(this.pagedMechanisms);
  }
}
