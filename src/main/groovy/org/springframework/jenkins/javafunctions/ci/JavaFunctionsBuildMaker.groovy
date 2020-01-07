package org.springframework.jenkins.javafunctions.ci

import javaposse.jobdsl.dsl.DslFactory
import org.springframework.jenkins.common.job.Cron
import org.springframework.jenkins.common.job.JdkConfig
import org.springframework.jenkins.common.job.Maven
import org.springframework.jenkins.common.job.TestPublisher
import org.springframework.jenkins.javafunctions.common.JavaFunctionsCommons

/**
 * @author Soby Chacko
 */
class JavaFunctionsBuildMaker implements JdkConfig, TestPublisher,
        Cron, JavaFunctionsCommons, Maven {

    private final DslFactory dsl
    final String organization
    final String repo

    String branchToBuild = "master"

    Map<String, Object> envVariables = new HashMap<>();

    JavaFunctionsBuildMaker(DslFactory dsl, String organization, String repo) {
        this.dsl = dsl
        this.organization = organization
        this.repo = repo
    }

    void deploy(boolean checkTests = true, boolean isGaRelease = false) {
        String projectBranch = branchToBuild

        dsl.job("${prefixJob(repo)}-${projectBranch}-ci") {
            triggers {
                if (!isGaRelease) {
                    githubPush()
                }
            }
            jdk jdk8()
            wrappers {
                colorizeOutput()
                environmentVariables(envVariables)
                credentialsBinding {
                    file('FOO_SEC', "spring-signing-secring.gpg")
                    file('FOO_PUB', "spring-signing-pubring.gpg")
                    string('FOO_PASSPHRASE', "spring-gpg-passphrase")
                    usernamePassword('SONATYPE_USER', 'SONATYPE_PASSWORD', "oss-token")
                }
            }
            scm {
                git {
                    remote {
                        url "https://github.com/${organization}/${repo}"
                        branch branchToBuild
                    }
                }
            }
            steps {
                shell(cleanAndDeploy(isGaRelease))
            }
            if (checkTests) {
                publishers {
                    archiveJunit mavenJUnitResults()
                }
            }
        }
    }
}

