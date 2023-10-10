import {AppVersion} from '../app/app.version';

export const environment = {
  VERSION: AppVersion.appVersion,
  production: true,
  apiUri: 'https://grove.craicic.dev:8443',
  filePrefix: 'data:image/jpg;base64,',
  api: {
    country: 'https://restcountries.com/v3.1/all'
  }
};
