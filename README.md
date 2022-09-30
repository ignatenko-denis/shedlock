# shedlock with spring - distributed lock for your scheduled tasks

Add table to database
```sql
drop table if exists shedlock;

create table shedlock
(
    name       varchar(64)  not null,
    lock_until timestamp    not null,
    locked_at  timestamp    not null,
    locked_by  varchar(255) not null,
    primary key (name)
);
```
For testing run at least 2 instance of this service with different ports.
```shell
java -jar ./target/shedlock-1.0.0.jar --server.port=9000
java -jar ./target/shedlock-1.0.0.jar --server.port=8000
```

[ShedLock](https://github.com/lukas-krecan/ShedLock) makes sure that your scheduled tasks are executed at most once at the same time. 
If a task is being executed on one node, it acquires a lock which prevents execution of the same task from another node (or thread). 
Please note, that if one task is already being executed on one node, 
execution on other nodes does not wait, it is simply skipped.

ShedLock uses an external store like Mongo, JDBC database, Redis, Hazelcast, ZooKeeper or others for coordination.
