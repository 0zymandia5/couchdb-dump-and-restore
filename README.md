# couchdb-dump-and-restore

If your database is large you may need to increase the resources for SBT to avoid "java.lang.OutOfMemoryError: Java heap space"

```
export SBT_OPTS="-XX:+CMSClassUnloadingEnabled -Xmx10G -Xms10G"
```

Create an .env file at root folder repository with the following env vars:

```
HOSTDB_CLOUDANT=
USER_CLOUDANT=
PASS_CLOUDANT=
DBNAME_CLOUDANT=
DBNAME_RESTORE_CLOUDANT=
DUMPS=True
RESTORE=True
```
