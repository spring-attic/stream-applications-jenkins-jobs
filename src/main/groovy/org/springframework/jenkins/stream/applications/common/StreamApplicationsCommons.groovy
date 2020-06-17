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
            'ftp-source'               : 'source/ftp-source',
            'geode-source'             : 'source/geode-source',
            'http-source'              : 'source/http-source',
            'jdbc-source'              : 'source/jdbc-source',
            'jms-source'               : 'source/jms-source',
            'load-generator-source'    : 'source/load-generator-source',
            'mongodb-source'           : 'source/mongodb-source',
            'mqtt-source'              : 'source/mqtt-source',
            'rabbit-source'            : 'source/rabbit-source',
            'tcp-source'               : 'source/tcp-source',
            'time-source'              : 'source/time-source',
            'websocket-source'         : 'source/websocket-source',
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
            'tasklauncher-sink'        : 'sink/tasklauncher-sink',
            'throughput-sink'          : 'sink/throughput-sink',
            'websocket-sink'           : 'sink/websocket-sink',
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

    public static final List<String> PHASE1_KEYS = ['file-source', 'ftp-source', 'geode-source', 'http-source', 'jdbc-source', 'jms-source','load-generator-source',
                                                    'mongodb-source', 'mqtt-source', 'rabbit-source']

    public static final List<String> PHASE2_KEYS = ['time-source', 'tcp-source', 'websocket-source', 'cassandra-sink', 'counter-sink', 'file-sink', 'ftp-sink', 'geode-sink',
                                                    'jdbc-sink', 'log-sink']

    public static final List<String> PHASE3_KEYS = ['mongodb-sink', 'mqtt-sink', 'rabbit-sink', 'redis-sink', 'router-sink', 'sftp-sink', 'tcp-sink',
                                                    'tasklauncher-sink', 'throughput-sink', 'websocket-sink']

    public static final List<String> PHASE4_KEYS = ['bridge-processor', 'filter-processor', 'groovy-processor',
                                                    'header-enricher-processor', 'http-request-processor', 'script-processor', 'splitter-processor', 'transform-processor']

    public static final List<List<String>> ALL_JOBS = [PHASE1_KEYS, PHASE2_KEYS, PHASE3_KEYS, PHASE4_KEYS]

}
