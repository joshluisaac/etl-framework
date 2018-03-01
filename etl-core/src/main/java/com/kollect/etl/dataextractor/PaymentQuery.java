package com.kollect.etl.dataextractor;

public class PaymentQuery implements IQuery {
  
  private static final String LINE_OF_BIZ = " as line_of_business ";
  
  public String query(final String dbName){ 
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
