package com.kollect.etl.dataextractor;

public class QueryStringFetcher {
  
  private QueryStringFetcher() {}
  
  private static final String LINE_OF_BIZ = " as line_of_business ";
  
  public static String getCustomerQuery(final String dbName){ 
    String databaseName = "'" + dbName + "'";
    return "select  "
        + "coalesce(customer.AccNo, '') AS customer_no, "
        + "coalesce(customer.CompanyName, '') AS customer_name, "
        + "coalesce(customer.Attention, '') AS person_in_charge, "
        + "coalesce(customer.Address1, '') AS address_1, "
        + "coalesce(customer.Address2, '') AS address_2, "
        + "coalesce(customer.Address3, '') AS address_3, "
        + "coalesce(customer.Address4, '') AS address_4, "
        + "coalesce(customer.PostCode, '') AS postcode, "
        + "coalesce(customer.Phone1, '') AS office_phone_1, "
        + "coalesce(customer.Phone2, '') AS office_phone_2, "
        + "coalesce(customer.Fax1, '') AS fax_1, "
        + "coalesce(customer.Fax2, '') AS fax_2, "
        + "coalesce(customer.EmailAddress, '') AS email, "
        + "coalesce(customer.SalesAgent, '') AS sales_agent, "
        + "customer.CreatedTimeStamp AS created_at, "
        + "customer.LastModified AS updated_at, "
        + "coalesce(customer.RegisterNo, '') AS register_no, "
        +  databaseName + LINE_OF_BIZ
        + "FROM Debtor AS customer "
        + "where customer.RegisterNo <> '' order by customer_name asc";
  }
  
  
  public static String getInvoiceQuery(final String dbName){ 
    String databaseName = "'" + dbName + "'";
    return " select "
        + "coalesce(invoices.DebtorCode, '') AS customer_no,"
        + "coalesce(invoices.DocNo,'') AS invoice_no,"
        + "invoices.DocDate AS invoice_date,"
        + "invoices.DueDate AS invoice_due_date,"
        + "invoices.Total2 AS invoice_amount,"
        + "invoices.TaxCurrencyTax AS invoice_gst,"
        + "invoices.Outstanding AS invoice_outstanding,"
        + "invoices.CreatedTimeStamp AS created_at,"
        + "invoices.LastModified AS updated_at,"
        + "coalesce(invoices.Description, '') AS Description,"
        +  databaseName + LINE_OF_BIZ
        + "FROM ARInvoice AS invoices "
        + " inner join Debtor customer on invoices.DebtorCode = customer.AccNo "
        + "where invoices.Cancelled = 'F' "
        + "and customer.RegisterNo <> ''";
  }
  

  public static String getCreditNoteQuery(final String dbName){ 
    String databaseName = "'" + dbName + "'";
    return "select "
        + "coalesce(transactions.DebtorCode, '') AS customer_no, "
        + "coalesce(transactions.DocNo, '') AS transaction_id,"
        + "coalesce(transactions.Description, '') AS description,"
        + "transactions.DocDate AS posted_date,"
        + "transactions.CreatedTimeStamp AS created_at,"
        + "transactions.LastModified AS updated_at,"
        + "transactions_knockoff.Amount AS amount,"
        + "coalesce(invoices.DocNo, '') AS invoice_no,"
        +  databaseName + LINE_OF_BIZ
        + "FROM ARCN AS transactions "
        + "inner join ARCNKnockOff AS transactions_knockoff ON transactions.DocKey=transactions_knockoff.DocKey "
        + "inner join ARInvoice AS invoices ON invoices.DocKey = transactions_knockoff.KnockOffDocKey "
        + "inner join Debtor customer on invoices.DebtorCode = customer.AccNo "
        + "where transactions.Cancelled = 'F' "
        + "and customer.RegisterNo <> '' "
        + "order by invoices.DebtorCode asc";
  }
  
  
  
  
  public static String getPaymentQuery(final String dbName){ 
    String databaseName = "'" + dbName + "'";
    return "select "
        + "coalesce(transactions.DebtorCode, '') AS customer_no, "
        + "coalesce(transactions.DocNo, '') AS transaction_id,"
        + "coalesce(transactions.Description, '') AS description,"
        + "transactions.DocDate AS posted_date,"
        + "transactions.CreatedTimeStamp AS created_at,"
        + "transactions.LastModified AS updated_at,"
        + "transactions_knockoff.Amount AS amount,"
        + "coalesce(invoices.DocNo, '') AS invoice_no, "
        +  databaseName + LINE_OF_BIZ
        + "FROM ARPayment AS transactions "
        + "inner join ARPaymentKnockOff AS transactions_knockoff ON transactions.DocKey=transactions_knockoff.DocKey "
        + "inner join ARInvoice AS invoices ON invoices.DocKey = transactions_knockoff.KnockOffDocKey "
        + "inner join Debtor customer on invoices.DebtorCode = customer.AccNo "
        + "where transactions.Cancelled='F' "
        + "and customer.RegisterNo <> '' "
        + "order by invoices.DebtorCode asc";
  }

}
