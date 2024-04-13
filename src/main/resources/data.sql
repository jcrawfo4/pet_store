
INSERT INTO customer (customer_id, customer_first_name, customer_last_name, customer_email)
VALUES (1, 'John', 'Doe', 'john.doe@example.com');
INSERT INTO customer (customer_id, customer_first_name, customer_last_name, customer_email)
VALUES (2, 'Jane', 'Doe', 'jane.doe@example.com'),
       (3, 'Alice', 'Smith', 'alice.smith@example.com'),
       (4, 'Bob', 'Johnson', 'bob.johnson@example.com'),
       (5, 'Charlie', 'Brown', 'charlie.brown@example.com');



INSERT INTO employee (employee_id, pet_store_id, employee_first_name, employee_last_name, employee_phone, employee_job_title)
VALUES (1, 1, 'John', 'Doe', '123-456-7890', 'Manager'),
       (2, 1, 'Jane', 'Doe', '123-456-7891', 'Sales Associate'),
       (3, 2, 'Alice', 'Smith', '123-456-7892', 'Manager'),
       (4, 2, 'Bob', 'Johnson', '123-456-7893', 'Sales Associate');



INSERT INTO pet_store (pet_store_id, pet_store_address, pet_store_city, pet_store_name, pet_store_phone, pet_store_state, pet_store_zip)
VALUES (1, '123 Main St', 'Olney', 'Pet World', '123-456-7890', 'MD', '20832'),
       (2, '456 Elm St', 'Rockville', 'Pet Emporium', '123-456-7891', 'MD', '20852');