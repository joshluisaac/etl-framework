package com.kollect.etl.extractor;

import com.kollect.etl.util.FileUtils;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataWriter {
  
  private static final Logger LOG = LoggerFactory.getLogger(DataWriter.class);

    @SuppressWarnings("rawtypes")
    public Query write(final String dstDir, final String filePrefix, List<String> data){
        final String fileName = filePrefix + ".csv";
        final long writeStart = System.currentTimeMillis();
        final long[] writeStat = writeFile(dstDir, fileName, data);
        final long writeDiff = DataExtractorUtils.getDifference(writeStart, System.currentTimeMillis());
        LOG.debug("Number of bytes written: {}", writeStat[0]);
        LOG.debug("Number of rows written: {}", writeStat[1]);
        LOG.debug("Writing to disk: {}ms", writeDiff);
        Query query = new Query<>();
        query.setNumberOfBytes(writeStat[0]);
        query.setRowsWritten(writeStat[1]);
        return query;
    }

    public long[] writeFile( final String dstDir, final String fileName, final List<String> data ) {
        File file = new File(dstDir + "/" + fileName);
        long rowsWritten = new FileUtils().writeListToFile(file, data, true);
        long numberOfBytes = file.length();
        return new long[] {numberOfBytes,rowsWritten};
    }

}
