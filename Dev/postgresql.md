### Postgresql

##### Get the disk space used by some objects in human-readable format

Use `pg_size_pretty(db_object)` where `db_object` is the result of calling a database object size function:

```sql

-- Database
SELECT pg_size_pretty(pg_database_size('dbname'));

-- Table
SELECT pg_size_pretty(pg_table_size('tablename'));

-- Indexes on a table
SELECT pg_size_pretty(pg_indexes_size('tablename'));
```

See the [Documentation](http://www.postgresql.org/docs/current/static/functions-admin.html#FUNCTIONS-ADMIN-DBOBJECT) for more.