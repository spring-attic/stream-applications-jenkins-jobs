package org.springframework.jenkins.stream.applications.common

import groovy.transform.CompileStatic

/**
 * @author Soby Chacko
 */
@CompileStatic
class StreamApplicationsCommons {

    public static final Map<String, String> PHASED_JOBS = [
            //sources
            'file-source'              : 'source/file-source',
            'geode-source'             : 'source/geode-source',
            'http-source'              : 'source/http-source',
            'jdbc-source'              : 'source/jdbc-source',
            'load-generator-source'    : 'source/load-generator-source',
            'mongodb-source'           : 'source/mongodb-source',
            'mqtt-source'              : 'source/mqtt-source',
            'rabbit-source'            : 'source/rabbit-source',
            'tcp-source'               : 'source/tcp-source',
            'time-source'              : 'source/time-source',
            //sinks
            'cassandra-sink'           : 'sink/cassandra-sink',
            'counter-sink'             : 'sink/counter-sink',
            'file-sink'                : 'sink/file-sink',
            'ftp-sink'                 : 'sink/ftp-sink',
            'geode-sink'               : 'sink/geode-sink',
            'jdbc-sink'                : 'sink/jdbc-sink',
            'log-sink'                 : 'sink/log-sink',
            'mongodb-sink'             : 'sink/mongodb-sink',
            'mqtt-sink'                : 'sink/mqtt-sink',
            'rabbit-sink'              : 'sink/rabbit-sink',
            'redis-sink'               : 'sink/redis-sink',
            'router-sink'              : 'sink/router-sink',
            'sftp-sink'                : 'sink/sftp-sink',
            'tcp-sink'                 : 'sink/tcp-sink',
            'throughput-sink'          : 'sink/throughput-sink',
            //processors
            'bridge-processor'         : 'processor/bridge-processor',
            'filter-processor'         : 'processor/filter-processor',
            'groovy-processor'         : 'processor/groovy-processor',
            'header-enricher-processor': 'processor/header-enricher-processor',
            'http-request-processor'   : 'processor/http-request-processor',
            'script-processor'         : 'processor/script-processor',
            'splitter-processor'       : 'processor/splitter-processor',
            'transform-processor'      : 'processor/transform-processor',
    ]

    public static final List<String> PHASE1_KEYS = ['file-source', 'geode-source', 'http-source', 'jdbc-source', 'load-generator-source',
                                                    'mongodb-source', 'mqtt-source', 'rabbit-source', 'tcp-source', 'time-source']

    public static final List<String> PHASE2_KEYS = ['cassandra-sink', 'counter-sink', 'file-sink', 'ftp-sink', 'geode-sink',
                                                    'jdbc-sink', 'log-sink', 'mongodb-sink', 'mqtt-sink', 'rabbit-sink']

    public static final List<String> PHASE3_KEYS = ['redis-sink', 'router-sink', 'sftp-sink', 'tcp-sink', 'throughput-sink',
                                                    'bridge-processor', 'filter-processor', 'groovy-processor',
                                                    'header-enricher-processor', 'http-request-processor']

    public static final List<String> PHASE4_KEYS = ['script-processor', 'splitter-processor', 'transform-processor']

    public static final List<List<String>> ALL_JOBS = [PHASE1_KEYS, PHASE2_KEYS, PHASE3_KEYS, PHASE4_KEYS]

}
