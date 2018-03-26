package com.kollect.etl.entity;

public class BatchHistory {
    private Integer id, batch_id, number_of_records_updated;
    private java.sql.Timestamp updated_date;

    public BatchHistory(Integer batch_id, Integer number_of_records_updated, java.sql.Timestamp updated_date){
        super();
        this.batch_id=batch_id;
        this. number_of_records_updated = number_of_records_updated;
        this.updated_date = updated_date;
        }
}
