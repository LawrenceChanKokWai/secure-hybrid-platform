#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
CREATE DATABASE auth_db;
CREATE DATABASE user_db;
CREATE DATABASE security_db;

CREATE USER auth_user WITH PASSWORD 'auth_password';
CREATE USER user_service_user WITH PASSWORD 'user_password';
CREATE USER security_user WITH PASSWORD 'security_password';

CREATE ROLE replicator WITH REPLICATION LOGIN PASSWORD 'replicator_password';

GRANT ALL PRIVILEGES ON DATABASE auth_db TO auth_user;
GRANT ALL PRIVILEGES ON DATABASE user_db TO user_service_user;
GRANT ALL PRIVILEGES ON DATABASE security_db TO security_user;
EOSQL

echo "host replication replicator 0.0.0.0/0 trust" >> "$PGDATA/pg_hba.conf"