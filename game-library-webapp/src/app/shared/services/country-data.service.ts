import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {map} from 'rxjs/operators';
import {Country} from '../../model/country.model';

@Injectable({
  providedIn: 'root'
})
export class CountryDataService {

  countries: string[];

  constructor(private http: HttpClient) {
    this.countries = [];
  }

  getList(): void {
    this.http
      .get<Country[]>(environment.api.country + '?fields=translations')
      .pipe(
        map(value => value.map(c => c.translations.fr)
          .filter(name => name)
          .map(name => this.countries.push(name))
        ))
      .subscribe();
  }
}
