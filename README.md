# File Store project


## Prerequisites

- [`Java 8`](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [`Docker`](https://www.docker.com/)
- [`Docker-Compose`](https://docs.docker.com/compose/install/)

## Start Environment

- Open a terminal and inside `file-manager` root folder run

  mvn clean install
  ```
   docker-compose  up  --build -d  
  ```

- Wait a little bit until `mysql` container is Up (healthy). You can check their status running
  ```
  docker-compose ps
  ```

## Running file-app using Maven & Npm

- **file-api**

    - Open a terminal and navigate to `/file-manager` folder

    - Run the following `Maven` command to start the application
      ```
      mvn clean package 
      ```

## Applications URLs

| Application | URL                                   | Credentials                                         |
|-------------| ------------------------------------- | --------------------------------------------------- |
| file-api    | http://localhost:8080/swagger-ui.html |        `user/user`                                             |

Use filemanager  (http, Bearer) authentication with the username = user and password=user ,  user:user
> **Note:** the credentials shown in the table are the ones already pre-defined.


## Shutdown


- To stop and remove docker-compose containers, networks and volumes, run the command below in `file-manager` root
  folder
  ```
  docker-compose down -v
  ```

