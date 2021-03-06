--  CREATE TABLE ITEMS( ITEM_I SERIAL, SKU TEXT NOT NULL, ITEM_DESCRIPTION TEXT DEFAULT NULL, PRICE NUMERIC (10,5), CRTE_TS TIMESTAMPTZ NULL DEFAULT current_timestamp);

CREATE TABLE COUNTRY(
    COUNTRY_ID SERIAL,
    COUNTRY_NAME TEXT,
    COUNTRY_CAPITAL TEXT,
    COUNTRY_ALPHA3CODE TEXT,
    COUNTRY_POPULATION NUMERIC,
    CREATED_TS TIMESTAMPTZ NULL DEFAULT current_timestamp);

select * from country;

INSERT INTO COUNTRY (COUNTRY_NAME, COUNTRY_CAPITAL,COUNTRY_ALPHA3CODE, COUNTRY_POPULATION) VALUES ('testName','testCapital','testAlpj',1234);