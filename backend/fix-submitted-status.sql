-- Fix loan applications with SUBMITTED status
-- Run this against loansys360_customer database

USE loansys360_customer;

-- Check current status values
SELECT application_id, customer_id, status FROM loan_applications;

-- Update SUBMITTED to PENDING (since SUBMITTED is not used in frontend)
UPDATE loan_applications 
SET status = 'PENDING' 
WHERE status = 'SUBMITTED';

-- Verify the update
SELECT application_id, customer_id, status FROM loan_applications;
