import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Image} from '../../model/image.model';
import {BehaviorSubject, Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {tap} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class ImageService {
  apiUri: string;
  images$: BehaviorSubject<Image[]> = new BehaviorSubject<Image[]>([]);

  constructor(private http: HttpClient) {
    this.apiUri = environment.apiUri;
  }


  /* ============================================== REST API METHODS =================================================================== */
  fetchImage(id: number): Observable<Image> {
    return this.http
      .get(this.apiUri + '/api/admin/images/' + id, {responseType: 'json'});
  }

  fetchImages(gameId: number): Observable<Image[]> {
    return this.http.get<Image[]>(this.apiUri + '/api/admin/games/' + gameId + '/images', {responseType: 'json'})
      .pipe(tap(i => {
        this.images$.next(i);
      }));

  }

  uploadImage(file: File, gameId: number): Observable<Image> {
    const fd: FormData = new FormData();
    const hd: HttpHeaders = new HttpHeaders();

    hd.append('Content-Type', undefined);

    fd.append('file', file);
    return this.http.post<Image>(this.apiUri + '/api/admin/images/games/' + gameId, fd, {headers: hd});
  }

  delete(id: number): Observable<any> {
    return this.http.delete(this.apiUri + '/api/admin/images/' + id);
  }

  /* ================================================ OTHER METHODS ==================================================================== */

  updateImages(image: Image): void {
    const images = this.getImages();
    images.push(image);
    this.images$.next(images);
  }

  removeImage(id: number): void {
    const images = this.getImages();
    const idx = images.findIndex(item => item.id === id);
    images.splice(idx, 1);
    this.images$.next(images);

  }

  getImages(): Image[] {
    return this.images$.value.slice();
  }
}
