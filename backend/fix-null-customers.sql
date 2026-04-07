-- Fix customers with null firstName/lastName/email/phone
-- Run this against loansys360_customer database

USE loansys360_customer;

-- Check which customers have null fields
SELECT customer_id, first_name, last_name, email, phone FROM customers WHERE first_name IS NULL OR email IS NULL;

-- Update null records with placeholder data so they are visible
-- Replace these values with real data as needed
UPDATE customers SET
    first_name  = CONCAT('Customer', customer_id),
    last_name   = 'Unknown',
    email       = CONCAT('customer', customer_id, '@example.com'),
    phone       = CONCAT('000000000', customer_id)
WHERE first_name IS NULL OR first_name = '';
