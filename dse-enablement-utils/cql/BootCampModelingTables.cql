CREATE KEYSPACE IF NOT EXISTS bootcamp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};

CREATE TABLE IF NOT EXISTS user (
    unique      timeuuid,
    rdb_seq_num int,
    first_name  text,
    last_name   text,
    mid_name    text,
    ordinal     text,
    birthday    date,
    occupation  text,
    title       text,
    PRIMARY KEY(unique)
) WITH comment = 'Sample Relational Llike User table design';

CREATE TABLE IF NOT EXISTS address (
    unique          timeuuid,
    rdb_adr_seq_num int,
    rdb_usr_seq_num int,
    address1        text,
    address2        text,
    address3        text,
    city            text,
    state           text,
    zip             text,
    country         text,
    PRIMARY KEY(unique)
) WITH comment = 'Sample Relational Address linked to user';

CREATE TABLE IF NOT EXISTS user_address (
    unique          timeuuid,
    rdb_usr_seq_num int,
    first_name      text,
    last_name       text,
    mid_name        text,
    ordinal         text,
    birthday        date,
    occupation      text,
    title           text,
    rdb_adr_seq_num int,
    address1        text,
    address2        text,
    address3        text,
    city            text,
    state           text,
    zip             text,
    country         text,
    PRIMARY KEY (unique) 
) WITH comment = 'Single table with user_address, but can only contain one address';

CREATE TYPE IF NOT EXISTS address_type (
    address1        text,
    address2        text,
    address3        text,
    city            text,
    state           text,
    zip             text,
    country         text
);

CREATE TABLE IF NOT EXISTS user_address_with_type (
    unique          timeuuid,
    first_name      text,
    last_name       text,
    mid_name        text,
    ordinal         text,
    birthday        date,
    occupation      text,
    title           text,
    address         FROZEN<address_type>,
    PRIMARY KEY (unique) 
) WITH comment = 'Single table with user_address as a UDT but still can only contain one address, note the frozen';


CREATE TABLE IF NOT EXISTS user_address_collection_type (
    unique          timeuuid,
    first_name      text,
    last_name       text,
    mid_name        text,
    ordinal         text,
    birthday        date,
    occupation      text,
    title           text,
    address         map<text, FROZEN<address_type>>,
    PRIMARY KEY (unique) 
) WITH comment = 'Now can have multiple address_types with key defining each.  But still frozen';

CREATE TABLE IF NOT EXISTS user_address_with_type_and_cc (
    unique          timeuuid,
    first_name      text,
    last_name       text,
    mid_name        text,
    ordinal         text,
    birthday        date,
    occupation      text,
    title           text,
    address_name    text, // This is the clustering column, no way to limit values
    address         FROZEN<address_type>,
    PRIMARY KEY ((unique),address_name) 
) WITH comment = 'Single table with user_address as a UDT but now multiple by address_name, still frozen';

CREATE TABLE IF NOT EXISTS user_address_multiple (
    unique          timeuuid,
    first_name      text,
    last_name       text,
    mid_name        text,
    ordinal         text,
    birthday        date,
    occupation      text,
    title           text,
    address_name    text,
    address1        text,
    address2        text,
    address3        text,
    city            text,
    state           text,
    zip             text,
    country         text,
    PRIMARY KEY ((unique), address_name) 
) WITH comment = 'Single table with user_address, now multiple address by cc, and each field can change independantly. But note in the record the name is repeated (and can be different for each address)';

CREATE TABLE IF NOT EXISTS user_address_multiple_static (
    unique timeuuid,
    first_name      text static,
    last_name       text static,
    mid_name        text static,
    ordinal         text static,
    birthday        date static,
    occupation      text static,
    title           text static,
    address_name    text,
    address1        text,
    address2        text,
    address3        text,
    city            text,
    state           text,
    zip             text,
    country         text,
    PRIMARY KEY ((unique), address_name) 
) WITH comment = 'Single table with user_address, now multiple address by cc, and each field can change independantly. Name info is not repeated saving disk, but then is not associated with each address as alias,etc';

