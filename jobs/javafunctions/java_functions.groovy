package javafunctions

import org.springframework.jenkins.javafunctions.ci.JavaFunctionsBuildMaker
import javaposse.jobdsl.dsl.DslFactory

DslFactory dsl = this

new JavaFunctionsBuildMaker(dsl, "pivotal", "java-functions").deploy()
