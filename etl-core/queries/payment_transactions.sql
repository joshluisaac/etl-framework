SELECT 
coalesce(transactions.DebtorCode, '') AS customer_no, 
coalesce(transactions.DocNo, '') AS transaction_id,
coalesce(transactions.Description, '') AS description,
transactions.DocDate AS posted_date,
transactions.CreatedTimeStamp AS created_at,
transactions.LastModified AS updated_at,
transactions_knockoff.Amount AS amount,
coalesce(invoices.DocNo, '') AS invoice_no,
'AED_ELEGANT_0'

FROM ARPayment AS transactions 
left join ARPaymentKnockOff AS transactions_knockoff ON transactions.DocKey=transactions_knockoff.DocKey
left join ARInvoice AS invoices ON invoices.DocKey = transactions_knockoff.KnockOffDocKey
left join Debtor customer on invoices.DebtorCode = customer.AccNo
WHERE transactions.Cancelled='F'
and customer.RegisterNo <> ''
order by invoices.DebtorCode asc;
