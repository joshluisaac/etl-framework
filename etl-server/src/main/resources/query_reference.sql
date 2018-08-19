 CASE
    WHEN mode = 'H' THEN
    credit = amount_local_curr ELSE 0 END AS credit,

    case
    WHEN mode = 'S' THEN
    debit = amount_local_curr ELSE 0 END AS debit,

    CASE WHEN mode = 'H' THEN
    type = PAYMENT
    WHEN mode = 'S' THEN
    type = INVOICE ELSE NULL END AS TYPE

    ---------------------------------------------
    SWITCH
    ---------------------------------------------
    CASE
    WHEN DOC_TYPE = 'AB' THEN
    CASE WHEN IS_INVOICE = true THEN
    'INVOICE' END END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'AB' THEN
    CASE
    WHEN IS_INVOICE = true THEN
    bill_doc END END AS INVOICE_NO


    CASE
    WHEN DOC_TYPE = 'RG' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'RG' THEN
    TRANSACTION_NO END AS INVOICE_NO

    //INVOICE = 1
    CASE
    WHEN DOC_TYPE ='YY' AND TYPE = 1 THEN
    'INVOICE'
    WHEN DOC_TYPE = 'YY'AND TYPE != 1 THEN
    'PAYMENT' ELSE NULL END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE ='YY' AND TYPE = 1 THEN
    TRANSACTION_NO ELSE null end as invoice_no,
    CASE
    WHEN DOC_TYPE = 'YY'AND TYPE != 1 THEN
    ORIGINAL_INVOICE_NO ELSE null END AS MOTHER_INVOICE_REF

    CASE
    WHEN DOC_TYPE = 'GI' AND TYPE = 1 THEN
    'INVOICE'
    WHEN DOC_TYPE = 'GI' AND TYPE != 1 THEN
    'PAYMENT' ELSE NULL END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'GI' AND TYPE = 1 THEN
    bill_doc ELSE null end as invoice_no,
    CASE
    WHEN DOC_TYPE = 'GI' AND TYPE != 1 THEN
    ORIGINAL_INVOICE_NO ELSE null END AS MOTHER_INVOICE_REF

    CASE
    WHEN DOC_TYPE = 'RI' AND TYPE = 1 THEN
    'LPC'
    WHEN DOC_TYPE = 'RI' AND TYPE != 1 THEN
    'PAYMENT' ELSE NULL END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'RI' AND TYPE = 1 THEN
    TRANSACTION_NO ELSE null end as invoice_no,
    CASE
    WHEN DOC_TYPE = 'RI' AND TYPE != 1 THEN
    ORIGINAL_INVOICE_NO ELSE null END AS MOTHER_INVOICE_REF


    CASE
    WHEN DOC_TYPE = 'RM' THEN
    'DEBIT' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'RM' THEN
    bill_doc END AS INVOICE_NO


    CASE
    WHEN doc_type = 'RV' AND (assignment = bill_doc) THEN
    'INVOICE'
    WHEN doc_type = 'RV' AND (assignent != bill_doc) AND type = 1 THEN
    'DEBIT'
    WHEN doc_type = 'RV' and (assignment != bill_doc)
    AND type != 1 THEN
    'CREDIT'END AS line_of_business,
    CASE
    WHEN doc_type = 'RV' AND
    (ASSIGNMENT != bill_doc) THEN mother_invoice_ref


    CASE
    WHEN DOC_TYPE = 'RY' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'RY' THEN
    bill_doc END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YC' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YC' THEN
    TRANSACTION_NO END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YD' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YD' THEN
    bill_doc END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YH' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YH' THEN
    bill_doc END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YI' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YI' THEN
    TRANSACTION_NO END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YJ' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YJ' THEN
    TRANSACTION_NO END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YL' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YL' THEN
    bill_doc END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YN' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YN' THEN
    TRANSACTION_NO END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YO' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YO' THEN
    bill_doc END AS INVOICE_NO


    //----------------NEED TO CHECK - isCommercialAccount function---------
    CASE
    WHEN DOC_TYPE = 'YP' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS,
    CASE
    WHEN DOC_TYPE = 'YP' THEN
    bill_doc END AS INVOICE_NO,
    CASE
    WHEN DOC_TYPE = 'YP' AND (//commercial condition//) AND type = 1 THEN
    TRANSACTION_NO
    WHEN doc_type = 'YP' and (//commercial condition//) AND type != 1 THEN
    bill_doc END AS line_of_business
    CASE
    WHEN DOC_TYPE = 'YP' THEN
    TRANSACTION_NO END AS INVOICE_NO
    //---------------------------------------------------------------------

    CASE
    WHEN DOC_TYPE = 'YQ' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YQ' THEN
    bill_doc END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YR' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YR' THEN
    bill_doc END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YS' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YS' THEN
    bill_doc END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YT' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YT' THEN
    bill_doc END AS INVOICE_NO

    CASE
    WHEN DOC_TYPE = 'YU' THEN
    'INVOICE' ELSE '' END AS LINE_OF_BUSINESS
    CASE
    WHEN DOC_TYPE = 'YU' THEN
    bill_doc END AS INVOICE_NO

    --------------------------------------------
    CASE YV Starts here
    --------------------------------------------

    CASE
    WHEN doc_type = 'YV' THEN 'INVOICE'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'YV' THEN bill_doc
    END AS invoice_no

    CASE
    WHEN doc_type = 'YW' THEN 'INVOICE'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'YW' THEN transaction_no
    END AS invoice_no

    CASE
    WHEN doc_type = 'YX' THEN 'INVOICE'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'YX' THEN bill_doc
    END AS invoice_no

    CASE
    WHEN doc_type = 'YK' THEN 'INVOICE'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'YK' THEN transaction_no
    END AS invoice_no

    CASE
    WHEN doc_type = 'Y1' THEN 'PAYMENT'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'Y1' THEN reference
    END AS mother_invoice_ref

    CASE
    WHEN doc_type = 'YE' THEN 'DEBIT'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'YE' THEN transaction_no
    END AS invoice_no

    CASE
    WHEN doc_type = 'YM' THEN 'CREDIT'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'YM' AND (bill_doc = null) THEN assignment
    WHEN doc_type = 'YM' AND (bill_doc != null) THEN bill_doc
    END AS mother_invoice_ref

    CASE
    WHEN doc_type = 'YF' THEN 'CREDIT'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'YF' THEN reference
    END AS mother_invoice_ref

    CASE
    WHEN doc_type = 'ZZ' AND type = 1 AND reference = 'RI'THEN
    'LPC'
    WHEN doc_type = 'ZZ' AND type = 1 AND reference != 'RI' THEN
    'INVOICE'
    WHEN doc_type = 'ZZ' and type != 1 THEN
    'PAYMENT'
    END AS line_of_business,
    CASE
    WHEN doc_type = 'ZZ' and type = 1 AND reference = 'RI'THEN
    transaction_no
    WHEN doc_type = 'ZZ' AND type = 1 AND reference != 'RI' THEN
    bill_doc
    END AS invoice_no,
    CASE
    WHEN doc_type = 'ZZ' and type != 1 THEN
    clearing_doc
    END AS mother_invoice_ref

    -------------------------------------------------------------
    Default Case
    -------------------------------------------------------------
    CASE
    WHEN doc_type != (ALL THE ABOVE) THEN
    'OTHERS'
    END AS line_of_business,
    CASE
    WHEN doc_type != (ALL THE ABOVE) THEN
    reference
    END AS mother_invoice_ref