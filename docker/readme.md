How to run
----------
1. cd path_to_project/support/docker
2. docker build . -t test_db
3. docker container run -p 5432:5432 --name test_db test_db

docker container start test_db