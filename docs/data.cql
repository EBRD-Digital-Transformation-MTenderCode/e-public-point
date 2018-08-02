CREATE KEYSPACE IF NOT EXISTS ocds WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS  ocds.notice_release (
    cp_id text,
    oc_id text,
    release_date timestamp,
    release_id text,
    stage text,
    json_data text,
    primary key(cp_id, oc_id, release_date)
)WITH CLUSTERING ORDER BY (oc_id DESC, release_date DESC);

CREATE TABLE IF NOT EXISTS ocds.notice_compiled_release (
       cp_id text,
       oc_id text,
       release_date timestamp,
       release_id text,
       stage text,
       json_data text,
       primary key(cp_id, oc_id)
)WITH CLUSTERING ORDER BY (oc_id ASC);

CREATE TABLE IF NOT EXISTS  ocds.notice_offset (
    cp_id text,
    release_date timestamp,
    primary key(cp_id)
);
