export interface PageCustom<T> {
  content?: T[];
  totalElements?: number;
  size?: number;
  pageSize?: number;
  pageNumber?: number;
}
