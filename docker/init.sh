#!/bin/bash
set -e
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    create role admin with login encrypted password 'test123';
    create database social_network with owner admin
EOSQL
