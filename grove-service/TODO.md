# TODO list

## V1 (Student project)

- [X] Game - Publisher : remove relationship. Create one to many between GameCopy and Publisher.

#### Model and DTO

- [X] Use lombok Equals and Hash to reduce code quantity.
- [X] Use lombok .Exclude.
- [x] Game - Image : set relationship to one to many.
- [X] CoreGame - Game : addCore, addExtension, and remove...
- [ ] Check for places where @Valid could have been forgotten
- [ ] Learn about and add @Version
- [ ] Define a dedicated hibernate sequence for each entity.
- [ ] Remove @Data lombok annotation and then create getter and setter wisely

#### Repository

- [X] Remove @Repository on JpaRepos.
- [ ] Users repository
- [X] Check the need of JpaRepos - Choose Interface that fits the requirement
- [ ] Take advantage of @EnableJpaRepositories to define a more specific package
- [ ] Look REALLY carefully at save method for already managed instance and remove the useless / problematic operation
  call
- [ ] Check for sorting on all List<T> usage
- [ ] Pagination : add Sort in each paginated for better results
- [X] Repository pattern !

#### Business layer

- [ ] Set mapping in business methods not in controller
- [ ] Migrate ALL EXCEPTIONS thrown by repository layer into the business one.

## V1.5 (Delivery)

- [ ] Improve logs.
- [ ] Improve documentation.
- [X] New constraint person concat(firstname,lastname) unique
- [ ] Membership number handling

## V2 (Update)

- [ ] Cron task to switch role of members who didn't pay the renewal of their membership