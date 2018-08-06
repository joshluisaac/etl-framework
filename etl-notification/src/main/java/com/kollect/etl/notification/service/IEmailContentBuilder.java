package com.kollect.etl.notification.service;

import java.util.List;

public interface IEmailContentBuilder {
    String buildSimpleEmail(String body, String templateName);

    String buildBatchUpdateEmail(String templateName,
                                 List<Object> uatStats, List<Object> prodStats);
}
