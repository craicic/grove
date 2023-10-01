import {AppVersion} from '../app/app.version';

export const environment = {
  VERSION: AppVersion.appVersion,
  production: true,
  apiUri: 'https://grove.craicic.dev',
  filePrefix: 'data:image/jpg;base64,',
  api: {
    country: 'https://restcountries.eu/rest/v2/all'
  }
};
