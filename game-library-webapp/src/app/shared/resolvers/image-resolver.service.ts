import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {ImageService} from '../services/image.service';
import {Observable} from 'rxjs';

export class ImageResolver implements Resolve<any> {

  constructor(private service: ImageService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return this.service.fetchImage(1);
  }
}
