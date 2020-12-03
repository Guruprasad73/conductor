/*
 * Copyright 2020 Netflix, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netflix.conductor.es6.config;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElasticSearchProperties {

    @Value("${workflow.elasticsearch.url:localhost:9300}")
    private String url;

    @Value("${workflow.elasticsearch.index.name:conductor}")
    private String indexName;

    @Value("${workflow.elasticsearch.tasklog.index.name:task_log}")
    private String taskLogIndexName;

    @Value("${workflow.elasticsearch.cluster.health.color:green}")
    private String clusterHealthColor;

    @Value("${workflow.elasticsearch.embedded.data.path:path.data}")
    private String embeddedDataPath;

    @Value("${workflow.elasticsearch.embedded.data.home:path.home}")
    private String embeddedHomePath;

    @Value("${workflow.elasticsearch.embedded.port:9200}")
    private int embeddedPort;

    @Value("${workflow.elasticsearch.embedded.cluster.name:elasticsearch_test}")
    private String embeddedClusterName;

    @Value("${workflow.elasticsearch.embedded.host:127.0.0.1}")
    private String embeddedHost;

    @Value("${workflow.elasticsearch.embedded.settings.file:embedded-es.yml}")
    private String embeddedSettingsFile;

    @Value("${workflow.elasticsearch.archive.search.batchSize:5000}")
    private int archiveSearchBatchSize;

    @Value("${workflow.elasticsearch.index.batchSize:1}")
    private int indexBatchSize;

    @Value("${workflow.elasticsearch.async.dao.worker.queue.size:100}")
    private int asyncWorkerQueueSize;

    @Value("${workflow.elasticsearch.async.dao.max.pool.size:12}")
    private int asyncMaxPoolSize;

    @Value("${workflow.elasticsearch.async.buffer.flush.timeout.seconds:10}")
    private int asyncBufferFlushTimeout;

    @Value("${workflow.elasticsearch.index.shard.count:5}")
    private int indexShardCount;

    @Value("${workflow.elasticsearch.index.replicas.count:1}")
    private int indexReplicasCount;

    @Value("${tasklog.elasticsearch.query.size:10}")
    private int taskLogResultLimit;

    @Value("${workflow.elasticsearch.rest.client.connectionRequestTimeout.milliseconds:-1}")
    private int restClientConnectionRequestTimeout;

    @Value("${workflow.elasticsearch.auto.index.management.enabled:true}")
    private boolean elasticSearchAutoIndexManagementEnabled;

    /**
     * Document types are deprecated in ES6 and removed from ES7. This property can be used to disable the use of
     * specific document types with an override. This property is currently used in ES6 module.
     * <p>
     * <em>Note that this property will only take effect if
     * {@link ElasticSearchProperties#isElasticSearchAutoIndexManagementEnabled} is set to false and index management is
     * handled outside of this module.</em>
     */
    @Value("${workflow.elasticsearch.document.type.override:}")
    private String elasticSearchDocumentTypeOverride;

    public String getURL() {
        return url;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getTaskLogIndexName() {
        return taskLogIndexName;
    }

    public String getClusterHealthColor() {
        return clusterHealthColor;
    }

    public String getEmbeddedDataPath() {
        return embeddedDataPath;
    }

    public String getEmbeddedHomePath() {
        return embeddedHomePath;
    }

    public int getEmbeddedPort() {
        return embeddedPort;
    }

    public String getEmbeddedClusterName() {
        return embeddedClusterName;
    }

    public String getEmbeddedHost() {
        return embeddedHost;
    }

    public String getEmbeddedSettingsFile() {
        return embeddedSettingsFile;
    }

    public int getArchiveSearchBatchSize() {
        return archiveSearchBatchSize;
    }

    public int getIndexBatchSize() {
        return indexBatchSize;
    }

    public int getAsyncWorkerQueueSize() {
        return asyncWorkerQueueSize;
    }

    public int getAsyncMaxPoolSize() {
        return asyncMaxPoolSize;
    }

    public int getAsyncBufferFlushTimeout() {
        return asyncBufferFlushTimeout;
    }

    public int getElasticSearchIndexShardCount() {
        return indexShardCount;
    }

    public int getElasticSearchIndexReplicasCount() {
        return indexReplicasCount;
    }

    public int getElasticSearchTasklogResultLimit() {
        return taskLogResultLimit;
    }

    public int getElasticsearchRestClientConnectionRequestTimeout() {
        return restClientConnectionRequestTimeout;
    }

    public boolean isElasticSearchAutoIndexManagementEnabled() {
        return elasticSearchAutoIndexManagementEnabled;
    }

    public String getElasticSearchDocumentTypeOverride() {
        return elasticSearchDocumentTypeOverride;
    }

    @VisibleForTesting
    public void setURL(String url) {
        this.url = url;
    }

    public List<URI> getURIs() {
        String clusterAddress = getURL();
        String[] hosts = clusterAddress.split(",");
        return Arrays.stream(hosts)
            .map(host ->
                (host.startsWith("http://") || host.startsWith("https://") || host.startsWith("tcp://"))
                    ? URI.create(host)
                    : URI.create("tcp://" + host)
            ).collect(Collectors.toList());
    }
}