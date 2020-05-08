package org.springframework.jenkins.stream.applications.common

import groovy.transform.CompileStatic

/**
 * @author Soby Chacko
 */
@CompileStatic
class StreamApplicationsCommons {

    public static final Map<String, String> PHASED_JOBS = ['jdbc-source':'source/jdbc-source', 'http-source':'source/http-source',
                                                           'splitter-processor':'processor/splitter-processor', 'rabbit-sink':'sink/rabbit-sink',
                                                           'time-source':'source/time-source', 'log-sink':'sink/log-sink',
                                                           'cassandra-sink':'sink/cassandra-sink', 'mongodb-sink':'sink/mongodb-sink',
                                                           'mongodb-source':'source/mongodb-source', 'filter-processor':'processor/filter-processor',
                                                           'transform-processor':'processor/transform-processor', 'jdbc-sink':'sink/jdbc-sink',
                                                           'counter-sink':'sink/counter-sink', 'bridge-processor':'processor/bridge-processor',
                                                           'file-sink':'sink/file-sink', 'file-source':'source/file-source',
                                                           'redis-sink':'sink/redis-sink', 'header-enricher-processor':'processor/header-enricher-processor']

    public static final List<String> PHASE1_KEYS = ['jdbc-source', 'http-source', 'splitter-processor', 'rabbit-sink', 'time-source', 'log-sink', 'cassandra-sink',
                                                    'mongodb-sink', 'mongodb-source', 'filter-processor']

    public static final List<String> PHASE2_KEYS = ['transform-processor', 'jdbc-sink', 'counter-sink', 'bridge-processor', 'file-source', 'file-sink',
                                                    'redis-sink', 'header-enricher']

    public static final List<List<String>> ALL_JOBS = [PHASE1_KEYS, PHASE2_KEYS]

}
