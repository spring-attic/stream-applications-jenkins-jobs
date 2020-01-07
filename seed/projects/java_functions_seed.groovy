
job('java-functions-seed') {
    triggers {
        githubPush()
    }
    scm {
        git {
            remote {
                github('spring-cloud-stream-app-starters/java-functions-jenkins')
            }
            branch('master')
        }
    }
    steps {
        gradle("clean build")
        dsl {
            external('jobs/javafunctions/*.groovy')
            removeAction('DISABLE')
            removeViewAction('DELETE')
            ignoreExisting(false)
            additionalClasspath([
                    'src/main/groovy', 'src/main/resources', 'build/lib/*.jar'
            ].join("\n"))
        }
    }
}
