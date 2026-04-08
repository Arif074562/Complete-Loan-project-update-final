-- Update loan_accounts table to match new schema
ALTER TABLE loan_accounts 
DROP COLUMN sanctioned_amount,
DROP COLUMN interest_rate,
DROP COLUMN tenure_months,
DROP COLUMN start_date;

ALTER TABLE loan_accounts
ADD COLUMN disbursement_id BIGINT,
ADD COLUMN outstanding_balance DECIMAL(15,2),
ADD COLUMN next_due_date DATE,
ADD COLUMN emi_amount DECIMAL(15,2);
