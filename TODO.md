# TODO list

## V1 (Student project)

- [X] Game - Publisher : remove relationship. Create one to many between GameCopy and Publisher.

#### Model and DTO

- [X] Use lombok Equals and Hash to reduce code quantity.
- [X] Use lombok .Exclude.
- [x] Game - Image : set relationship to one to many.
- [X] CoreGame - Game : addCore, addExtension, and remove...
- [ ] Check for places where @Valid could have been forgotten

#### Repository

- [X] Remove @Repository on JpaRepos.
- [ ] Users repository

## V1.5 (Delivery)
- [ ] Improve logs.
- [ ] Improve documentation.
- [ ] New constraint person concat(firstname,lastname) unique

## V2 (Update)
- [ ] Cron task to switch role of members who didn't pay the renewal of their membership