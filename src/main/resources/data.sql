INSERT INTO CUSTOMERS (id, first_name, last_name) VALUES (1, 'Spider', 'Man');
INSERT INTO CUSTOMERS (id, first_name, last_name) VALUES (2, 'Iron', 'Man');
INSERT INTO CUSTOMERS (id, first_name, last_name) VALUES (3, 'Captain', 'America');

INSERT INTO PHONE_NUMBERS (phone_number, customer_id) VALUES ('0411222133', 1);
INSERT INTO PHONE_NUMBERS (phone_number, customer_id, activated, date_activated) VALUES ('0411222134', 1, true, CURRENT_DATE);
INSERT INTO PHONE_NUMBERS (phone_number, customer_id) VALUES ('0411222135', 1);
INSERT INTO PHONE_NUMBERS (phone_number, customer_id) VALUES ('0411222234', 2);
