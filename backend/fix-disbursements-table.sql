-- Add bank details columns to disbursements table
ALTER TABLE disbursements 
ADD COLUMN account_number VARCHAR(50),
ADD COLUMN ifsc_code VARCHAR(20),
ADD COLUMN bank_name VARCHAR(100);
