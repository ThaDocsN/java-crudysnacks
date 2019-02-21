CREATE TABLE IF NOT EXISTS customer (
    id         INTEGER PRIMARY KEY AUTOINCREMENT
                       NOT NULL,
    name       STRING,
    cashonhand DOUBLE,
    phone      STRING
);
CREATE TABLE IF NOT EXISTS snack (
    id        INTEGER PRIMARY KEY AUTOINCREMENT
                      NOT NULL
                      UNIQUE,
    name      STRING  UNIQUE
                      NOT NULL,
    quantity  INTEGER,
    cost      DOUBLE,
    vendingid INTEGER REFERENCES vendingmachine (id)
);
CREATE TABLE IF NOT EXISTS vendingmachine (
    id   INTEGER PRIMARY KEY AUTOINCREMENT
                 NOT NULL,
    name STRING  NOT NULL
                 UNIQUE
);