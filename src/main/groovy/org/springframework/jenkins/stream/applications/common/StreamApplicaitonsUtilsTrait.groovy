package org.springframework.jenkins.stream.applications.common

import org.springframework.jenkins.common.job.BuildAndDeploy

/**
 * @author Marcin Grzejszczak
 */
trait StreamApplicaitonsUtilsTrait extends BuildAndDeploy {

	@Override
	String projectSuffix() {
		return 'stream-applications'
	}

	/**
	 * Dirty hack cause Jenkins is not inserting Maven to path...
	 * Requires using Maven3 installation before calling
	 *
	 */
	String mavenBin() {
		return "/opt/jenkins/data/tools/hudson.tasks.Maven_MavenInstallation/maven33/apache-maven-3.3.9/bin/"
	}

	String setupGitCredentials() {
		return """
					set +x
					git config user.name "${githubUserName()}"
					git config user.email "${githubEmail()}"
					git config credential.helper "store --file=/tmp/gitcredentials"
					echo "https://\$${githubRepoUserNameEnvVar()}:\$${githubRepoPasswordEnvVar()}@github.com" > /tmp/gitcredentials
					set -x
				"""
	}

	String githubUserName() {
		return 'spring-buildmaster'
	}

	String githubEmail() {
		return 'buildmaster@springframework.org'
	}

	String githubRepoUserNameEnvVar() {
		return 'GITHUB_REPO_USERNAME'
	}

	String githubRepoPasswordEnvVar() {
		return 'GITHUB_REPO_PASSWORD'
	}

	String dockerHubUserNameEnvVar() {
		return 'DOCKER_HUB_USERNAME'
	}

	String dockerHubPasswordEnvVar() {
		return 'DOCKER_HUB_PASSWORD'
	}

	String cleanGitCredentials() {
		return "rm -rf /tmp/gitcredentials"
	}

	String cleanAndDeployFunctions(boolean isRelease, String releaseType) {
		if (isRelease && releaseType != null && !releaseType.equals("milestone")) {
			return """
                    #!/bin/bash -x

					cp mvnw functions
					cp -R .mvn functions
					cd functions

                    lines=\$(find . -type f -name pom.xml | xargs egrep "SNAPSHOT|M[0-9]|RC[0-9]" | grep -v ".contains(" | grep -v regex | wc -l)
                    if [ \$lines -eq 0 ]; then
                        set +x
                        ./mvnw clean deploy -Pspring -Dgpg.secretKeyring="\$${gpgSecRing()}" -Dgpg.publicKeyring="\$${
				gpgPubRing()}" -Dgpg.passphrase="\$${gpgPassphrase()}" -DSONATYPE_USER="\$${sonatypeUser()}" -DSONATYPE_PASSWORD="\$${sonatypePassword()}" -Pcentral -U
                        set -x
                        rm mvnw
                        rm -rf .mvn
                        cd ..
                    else
                        echo "Non release versions found. Exiting build"
                        exit 1
                    fi
                """
		}
		if (isRelease && releaseType != null && releaseType.equals("milestone")) {
			return """
					#!/bin/bash -x

					cp mvnw functions
					cp -R .mvn functions
					cd functions
					
			   		lines=\$(find . -type f -name pom.xml | xargs grep SNAPSHOT | grep -v ".contains(" | grep -v regex | wc -l)
					if [ \$lines -eq 0 ]; then
						./mvnw clean deploy -U -Pspring
						rm mvnw
                        rm -rf .mvn
                        cd ..
					else
						echo "Snapshots found. Exiting the release build."
						exit 1
					fi
			   """
		}
	}

	String cleanAndDeployCore(boolean isRelease, String releaseType) {
		if (isRelease && releaseType != null && !releaseType.equals("milestone")) {
			return """
                    #!/bin/bash -x

					cp mvnw applications/stream-applications-core
					cp -R .mvn applications/stream-applications-core
					cd applications/stream-applications-core

                    lines=\$(find . -type f -name pom.xml | xargs egrep "SNAPSHOT|M[0-9]|RC[0-9]" | grep -v ".contains(" | grep -v regex | wc -l)
                    if [ \$lines -eq 0 ]; then
                        set +x
                        ./mvnw clean deploy -f applications/stream-applications-core -Pspring -Dgpg.secretKeyring="\$${gpgSecRing()}" -Dgpg.publicKeyring="\$${
				gpgPubRing()}" -Dgpg.passphrase="\$${gpgPassphrase()}" -DSONATYPE_USER="\$${sonatypeUser()}" -DSONATYPE_PASSWORD="\$${sonatypePassword()}" -Pcentral -U
                        set -x
                        rm mvnw
                        rm -rf .mvn
                        cd ../..
                    else
                        echo "Non release versions found. Exiting build"
                        exit 1
                    fi
                """
		}
		if (isRelease && releaseType != null && releaseType.equals("milestone")) {
			return """
					#!/bin/bash -x
					
					cp mvnw applications/stream-applications-core
					cp -R .mvn applications/stream-applications-core
					cd applications/stream-applications-core
					
			   		lines=\$(find . -type f -name pom.xml | xargs grep SNAPSHOT | grep -v ".contains(" | grep -v regex | wc -l)
					if [ \$lines -eq 0 ]; then
						./mvnw clean deploy -f applications/stream-applications-core -U -Pspring
						
						rm mvnw
                        rm -rf .mvn
                        cd ../..
					else
						echo "Snapshots found. Exiting the release build."
						exit 1
					fi
			   """
		}
	}

	String cleanAndDeployWithGenerateApps(boolean isRelease, String releaseType, String cdToApps) {
		if (isRelease && releaseType != null && !releaseType.equals("milestone")) {
			return """
                    #!/bin/bash -x
                    pushd applications/${cdToApps}
                    rm -rf apps
					
                    lines=\$(find . -type f -name pom.xml | xargs egrep "SNAPSHOT|M[0-9]|RC[0-9]" | grep -v regex | wc -l)
                    if [ \$lines -eq 0 ]; then
                        set +x
						if [ -d "src/main/java" ]
						then
							echo "Source folder found."
							popd
							./mvnw clean deploy -U -f applications/${cdToApps}
						else
							popd
							./mvnw clean package -U -f applications/${cdToApps}
						fi
                        set -x
                    else
                        echo "Non release versions found. Exiting build"
						exit 1
                    fi
                """
		}
		if (isRelease && releaseType != null && releaseType.equals("milestone")) {
			return """
                #!/bin/bash -x
                cd applications/${cdToApps}
                rm -rf apps

                lines=\$(find . -type f -name pom.xml | xargs grep SNAPSHOT | wc -l)
                if [ \$lines -eq 0 ]; then
                     set +x
					 if [ -d "src/main/java" ]
					 then
						echo "Source folder found."
						cd -
						./mvnw clean deploy -U -f applications/${cdToApps}
					else
						cd -
						./mvnw clean package -U -f applications/${cdToApps}
					fi
                    set -x
                else
                    echo "Snapshots found. Exiting the release build."
					exit 1
                fi
           """
		}
	}

	String cleanAndInstallAggregate(boolean isRelease, String releaseType) {
		if (isRelease && releaseType != null && !releaseType.equals("milestone")) {
			return """
                    #!/bin/bash -x
                    rm -rf apps

                    lines=\$(find applications/stream-applications-build -type f -name pom.xml | xargs egrep "SNAPSHOT|M[0-9]|RC[0-9]" | grep -v ".contains(" | grep -v regex | wc -l)
                    if [ \$lines -eq 0 ]; then
                        set +x
                        ./mvnw clean deploy -f applications/stream-applications-build -Pspring -Dgpg.secretKeyring="\$${gpgSecRing()}" -Dgpg.publicKeyring="\$${
				gpgPubRing()}" -Dgpg.passphrase="\$${gpgPassphrase()}" -DSONATYPE_USER="\$${sonatypeUser()}" -DSONATYPE_PASSWORD="\$${sonatypePassword()}" -Pcentral -U
                        set -x
                    else
                        echo "Non release versions found. Exiting build."
                        exit 1
                    fi
                """
		}
		if (isRelease && releaseType != null && releaseType.equals("milestone")) {
			return """
					#!/bin/bash -x

			   		lines=\$(find applications/stream-applications-build -type f -name pom.xml | xargs grep SNAPSHOT | grep -v ".contains(" | grep -v regex | wc -l)
					if [ \$lines -eq 0 ]; then
						./mvnw clean install -f applications/stream-applications-build -U -Pspring
					else
						echo "Snapshots found. Exiting the release build."
						exit 1
					fi
			   """
		}
	}

	String gpgSecRing() {
		return 'FOO_SEC'
	}

	String gpgPubRing() {
		return 'FOO_PUB'
	}

	String gpgPassphrase() {
		return 'FOO_PASSPHRASE'
	}

	String sonatypeUser() {
		return 'SONATYPE_USER'
	}

	String sonatypePassword() {
		return 'SONATYPE_PASSWORD'
	}
}