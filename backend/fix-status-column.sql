-- Fix status column length in loan_applications table
ALTER TABLE loan_applications MODIFY COLUMN status VARCHAR(20);
