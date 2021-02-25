package streamapplications

import org.springframework.jenkins.stream.applications.ci.StreamApplicationsPhasedBuildMaker
import javaposse.jobdsl.dsl.DslFactory

DslFactory dsl = this

String releaseType = "ga" // possible values are - "", milestone or ga

// Master CI
new StreamApplicationsPhasedBuildMaker(dsl).build(true, releaseType)
