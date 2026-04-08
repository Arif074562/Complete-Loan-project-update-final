-- Check loan accounts in servicing database
SELECT * FROM loan_accounts;

-- Count active loan accounts
SELECT COUNT(*) as active_accounts FROM loan_accounts WHERE status = 'ACTIVE';
