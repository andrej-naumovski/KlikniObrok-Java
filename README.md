# KlikniObrok.mk
## Mobile-based application for standardized order systems in bars and restaurants.

### Rules for pushing commits
* All initial code commits go in ***dev*** branch
* Pull requests for ***staging*** are made only after ***dev*** branch has been confirmed working
* Pull requests for ***production*** are made only after ***staging*** branch has been tested
* Make more smaller commits instead of one big commit, makes it easier for debugging
* Commit messages should be in present simple, i.e *"Implement UserService login functionality"* instead of *"Implemented UserService login functionality"*
* Make commit messages short and understandable, i.e *"Implement UserService login functionality"* instead of *"Implement POST method with JWT for login functionality in UserService"*
* Try to commit buggy/unfinished code as rarely as possible :)
