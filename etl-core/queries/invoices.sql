SELECT 
coalesce(invoices.DebtorCode, '') AS customer_no,
coalesce(invoices.DocNo,'') AS invoice_no,
invoices.DocDate AS invoice_date,
invoices.DueDate AS invoice_due_date,
invoices.Total2 AS invoice_amount,
invoices.TaxCurrencyTax AS invoice_gst,
invoices.Outstanding AS invoice_outstanding,
invoices.CreatedTimeStamp AS created_at,
invoices.LastModified AS updated_at,
coalesce(invoices.Description, '') AS Description,
'AED_YYC_8'
FROM ARInvoice AS invoices
inner join Debtor customer on invoices.DebtorCode = customer.AccNo
WHERE invoices.Cancelled = 'F'
and customer.RegisterNo <> '';

SELECT 
coalesce(invoices.DebtorCode, '') AS customer_no,
coalesce(invoices.DocNo,'') AS invoice_no,
invoices.DocDate AS invoice_date,
invoices.DueDate AS invoice_due_date,
invoices.Total2 AS invoice_amount,
invoices.TaxCurrencyTax AS invoice_gst,
invoices.Outstanding AS invoice_outstanding,
invoices.CreatedTimeStamp AS created_at,w
invoices.LastModified AS updated_at,
coalesce(invoices.Description, '') AS Description,
'AED_YYC'
FROM ARInvoice AS invoices
inner join Debtor customer on invoices.DebtorCode = customer.AccNo
WHERE invoices.Cancelled = 'F'
and customer.RegisterNo <> '';

SELECT 
coalesce(invoices.DebtorCode, '') AS customer_no,
coalesce(invoices.DocNo,'') AS invoice_no,
invoices.DocDate AS invoice_date,
invoices.DueDate AS invoice_due_date,
invoices.Total2 AS invoice_amount,
invoices.TaxCurrencyTax AS invoice_gst,
invoices.Outstanding AS invoice_outstanding,
invoices.CreatedTimeStamp AS created_at,
invoices.LastModified AS updated_at,
coalesce(invoices.Description, '') AS Description,
'AED_ELEGANT_0'
FROM ARInvoice AS invoices
inner join Debtor customer on invoices.DebtorCode = customer.AccNo
WHERE invoices.Cancelled = 'F'
and customer.RegisterNo <> '';