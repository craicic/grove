import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
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
      .get(this.apiUri + '/api/admin/images/' + id, {responseType: 'json'});
  }

  uploadImage(file: File, gameId: number): Observable<any> {
    const fd: FormData = new FormData();
    const hd: HttpHeaders = new HttpHeaders();

    hd.append('Content-Type', undefined);

    fd.append('file', file);
    return this.http.post(this.apiUri + '/api/admin/images/games/' + gameId, fd, {headers: hd});
  }

  /* ================================================ OTHER METHODS ==================================================================== */
}
