SELECT 
coalesce(customer.AccNo, '') AS customer_no,
coalesce(customer.CompanyName, '') AS customer_name,
coalesce(customer.Attention, '') AS person_in_charge,
coalesce(customer.Address1, '') AS address_1,
coalesce(customer.Address2, '') AS address_2,
coalesce(customer.Address3, '') AS address_3,
coalesce(customer.Address4, '') AS address_4,
coalesce(customer.PostCode, '') AS postcode,
coalesce(customer.Phone1, '') AS office_phone_1,
coalesce(customer.Phone2, '') AS office_phone_2,
coalesce(customer.Fax1, '') AS fax_1,
coalesce(customer.Fax2, '') AS fax_2,
coalesce(customer.EmailAddress, '') AS email,
coalesce(customer.SalesAgent, '') AS sales_agent,
customer.CreatedTimeStamp AS created_at,
customer.LastModified AS updated_at,
coalesce(customer.RegisterNo, '') AS register_no,
'AED_YYC'
FROM Debtor AS customer
where customer.RegisterNo <> '';
