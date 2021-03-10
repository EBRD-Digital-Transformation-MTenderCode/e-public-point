CREATE TABLE IF NOT EXISTS ocds.record_package_metadata
(
    id        text,
    attribute text,
    value     text,
    primary key (id, attribute)
);