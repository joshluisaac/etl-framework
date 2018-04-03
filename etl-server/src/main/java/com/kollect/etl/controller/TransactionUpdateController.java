package com.kollect.etl.controller;

import com.kollect.etl.config.BatchConfig;
import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.entity.TransactionLoad;
import com.kollect.etl.service.*;
import com.kollect.etl.util.LogStats;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionUpdateController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionUpdateController.class);
    private ReadWriteServiceProvider rwProvider;
    private AsyncBatchService asyncService;
    private BatchHistoryService batchHistoryService;
    private static final Map<String, CrudProcessHolder> MAP = new HashMap<>();

    @Autowired
    public TransactionUpdateController(ReadWriteServiceProvider rwProvider,
                                       AsyncBatchService asyncService, BatchHistoryService batchHistoryService) {
        this.rwProvider = rwProvider;
        this.asyncService = asyncService;
        this.batchHistoryService = batchHistoryService;
        BatchConfig.buildHolderMap(MAP);
    }

    private <T> void invoke(List<T> list, final String updateQuery, final int thread, final int commitSize) {
        Iterator<T> itr = list.iterator();
        asyncService.execute(itr, new IRunnableProcess<TransactionLoad>() {
            @Override
            public void process(List<TransactionLoad> threadData) {
                long queryStart = System.currentTimeMillis();
                try (final SqlSession sqlSession = rwProvider.getBatchSqlSession();) {
                    for (int i = 0; i < threadData.size(); i++) {
                        sqlSession.update(updateQuery, threadData.get(i));
                    }
                    sqlSession.commit();
                    long queryEnd = System.currentTimeMillis();
                    LogStats.logQueryStatistics("parallelStream", updateQuery, queryStart, queryEnd);
                } catch (PersistenceException persEx) {
                    LOG.error("Failed to execute update statement: {}", updateQuery, persEx.getCause());
                    throw persEx;
                }
            }
        }, thread, commitSize);
    }

    public void process(final String docType) {
        CrudProcessHolder holder = MAP.get(docType);
        final int thread = holder.getThread();
        final int commitSize = holder.getCommitSize();
        final String queryName = holder.getQueryName();
        final String updateQuery = "updateTransactionLoad";
        List<TransactionLoad> list = rwProvider.executeQuery(queryName, null);
        int recordCount = list.size();
        invoke(list, updateQuery, thread, commitSize);
        holder.setRecordCount(recordCount);
    }

    @PostMapping(value = "/updateTrxLoadInvoicesByDocType")
    @ResponseBody
    public Object updateInvoicesByDocType(@RequestParam (required = false) Integer batch_id) {
        long startTime = System.nanoTime();
        List<String> docTypes = new ArrayList<>(Arrays.asList("AB", "RG", "YY", "CLEARING_DOC_BASED_TYPES", "GI", "RI", "RM",
                "RV", "RY", "YC", "YD", "YH", "YI", "YJ", "YL", "YN", "YO", "YP", "YQ", "YR", "YS", "YT", "YU", "YV",
                "YW", "YX", "YK", "Y1", "YE", "YM", "YF", "ZZ", "OTHERS"));
        for (String docType : docTypes) {
            process(docType);
        }
        long endTime = System.nanoTime();
        long timeTaken = (endTime - startTime) / 1000000;
        this.batchHistoryService.runBatchHistory(batch_id, 0, timeTaken);
        return 0;
    }

}
