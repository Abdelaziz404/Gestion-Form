# Running the Application with Docker

This guide explains how to run the full-stack application (Spring Boot + Angular + PostgreSQL) using Docker.

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/) installed on your machine.
- [Docker Compose](https://docs.docker.com/compose/install/) installed.

## How to Run

1.  **Open a terminal** in the project root directory (where the `docker-compose.yml` file is located).
2.  **Run the following command** to build and start all services:

    ```bash
    docker compose up --build
    ```

3.  **Wait for the services to start**. The first time you run this, it will download the necessary images and build the containers, which might take a few minutes.

## Accessing the Application

- **Frontend**: Open your browser and go to [http://localhost](http://localhost) (port 80).
- **Backend API**: Accessible at [http://localhost:8080](http://localhost:8080).
- **Database**: PostgreSQL is running on port 5432.

## Stopping the Application

To stop the containers, press `Ctrl + C` in the terminal where Docker Compose is running, or run:

```bash
docker compose down
```

## Troubleshooting

- If the backend fails to connect to the database, ensure that no other service is already using port 5432.
- The backend configuration uses environment variables defined in `docker-compose.yml` to override the defaults in `application.yaml`.
