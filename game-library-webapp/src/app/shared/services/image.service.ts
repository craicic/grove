import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Image} from '../../model/image.model';
import {Observable} from 'rxjs';
import {ConfigurationService} from '../../dashboard/configuration/configuration.service';
import {environment} from '../../../environments/environment';

@Injectable({providedIn: 'root'})
export class ImageService {
  apiUri: string;

  constructor(private http: HttpClient,
              private config: ConfigurationService) {
    this.apiUri = environment.apiUri;
  }


  /* ============================================== REST API METHODS =================================================================== */
  fetchImage(id: number): Observable<Image> {
    return this.http
      .get(this.apiUri + '/admin/images/' + id, {responseType: 'json'});
  }

  /* ================================================ OTHER METHODS ==================================================================== */

}
