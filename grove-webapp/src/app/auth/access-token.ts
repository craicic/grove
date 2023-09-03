export type AccessToken = {
  sub: string;
  exp: number;
  iat: number;
  iss: string;
  roles: string[];
};
