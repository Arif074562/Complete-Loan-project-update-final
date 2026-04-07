-- Ensure disbursements status column can hold all enum values
ALTER TABLE disbursements MODIFY COLUMN status VARCHAR(20);

-- Update any old PROCESSED values to COMPLETED
UPDATE disbursements SET status = 'COMPLETED' WHERE status = 'PROCESSED';
