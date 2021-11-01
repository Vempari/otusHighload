FROM postgres:13-alpine
ENV pgver 13
ENV POSTGRES_DB=test
ENV POSTGRES_USER=test
ENV POSTGRES_PASSWORD=test123
LABEL description="local testing database image"
COPY init.sh /docker-entrypoint-initdb.d/init.sh
EXPOSE 543
