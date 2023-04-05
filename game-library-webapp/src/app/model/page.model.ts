export class Page<T> {
  content?: T[];
  totalPages?: number;
  totalElements?: number;
  last?: boolean;
  size?: number;
  first?: boolean;
  sort?: string;
  numberOfElements?: number;
  pageable?: {
    offset?: number,
    pageSize?: number,
    pageNumber?: number,
    unpaged?: false,
    paged?: true
  };
}
