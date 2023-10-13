import {AuthenticationService} from './auth/authentication.service';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';


export function sessionInitializer(service: AuthenticationService): () => Observable<boolean | Jwt> {
  const accessToken = localStorage.getItem('access_token')?.toString();
  const refreshToken = localStorage.getItem('refresh_token')?.toString();
  if (!accessToken || !refreshToken) {
    console.log('access or refresh MISSING');
    return () => of(false);
  }

  service.loadProfile({access_token: accessToken, refresh_token: refreshToken});

  if (!service.accessExpired()) {
    console.log('access STILL VALID');
    // todo Sometime timer expire before timer is over.
    service.initTimer();
    return () => of(true);
  }

  if (!service.refreshExpired()) {
    console.log('access NOT VALID but refresh VALID');
    return () => service.refreshToken().pipe(
      catchError(() => of(false)));
  }

  return () => of(false);
}

type Jwt = {
  access_token: string;
  refresh_token: string;
};

