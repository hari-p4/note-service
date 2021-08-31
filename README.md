Steps to build image and bring the container up:
    -Run docker pull postgres
    -Run ./gradlew build under note-service
    -Run docker-compose up --build
    -Do docker ps, to make sure the containers are up and running on port 8080

Testing note-service application:
    -There are two default accounts with email and passkey:
        joe@gmail.com 'admin123' & lisa@gmail.com 'standard123'
    -To get access to app, log in first with    
            curl --location --request POST 'http://192.168.99.100:8080/auth' \
            --header 'Content-Type: application/json' \
            --data-raw '{
            "email": "<yourusername>",
            "password": "<yourpassword>"}'
    -To create a Note:  
            curl --location --request POST 'http://192.168.99.100:8080/api/v1.0/notes' \
            --header 'Content-Type: application/json' \
            --header 'Authorization: Bearer <token generated from login response> 
            --data-raw '{
            "title": "Lisa'\''s Note",
            "note": "Buy a new headphone"}'
    -To update a Note(use the same title):
            curl --location --request POST 'http://192.168.99.100:8080/api/v1.0/notes' \
            --header 'Content-Type: application/json' \
            --header 'Authorization: Bearer <token generated from login response>
            --data-raw '{
            "title": "Lisa'\''s Note",
            "note": "Buy a new headphone UPDATED"}'
    -To delete the note copy the id generated from above responses:
            curl --location --request DELETE 'http://192.168.99.100:8080/api/v1.0/notes/1' \
            --header 'Authorization: Bearer <token generated from login response>

Notes: 
1). This application will run on port 8080.
2). Host of this application is http://192.168.99.100:8080 since I was running it into docker toolbox, however host may differ according to environment.
3). postgres container will run on port 5432 with the user name 'postgres' and db name 'postgres'
4). To check the data present in database:
        e.g. docker exec -it <postgres container id> psql -U postgres -d postgres -p 5432
        postgres=# \dt
        postgres=# select * from notes;
        id |    title    |   note   |        create_time         |      last_update_time      | user_id
        ----+-------------+----------+----------------------------+----------------------------+---------
        1 | Lisa's note | Buy a new headphone | 2021-08-31 16:18:04.262+00 | 2021-08-31 16:18:04.262+00 |       2