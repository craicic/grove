import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

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
      .get<{translations: {fra: {common: string}}}[]>(environment.api.country + '?fields=translations')
      .subscribe(data => {
        console.log(data);
        data.forEach(c => this.countries.push(c.translations.fra.common));
      });
  }
}
