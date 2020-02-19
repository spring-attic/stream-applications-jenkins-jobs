package org.springframework.jenkins.javafunctions.common

import org.springframework.jenkins.common.job.BuildAndDeploy

/**
 * @author Soby Chacko
 */
trait JavaFunctionsCommons extends BuildAndDeploy {

    @Override
    String projectSuffix() {
        return 'java-functions'
    }

    String cleanAndDeploy(boolean isRelease, String releaseType) {
        if (isRelease && releaseType != null && !releaseType.equals("milestone")) {

            return  """
                        lines=\$(find . -type f -name pom.xml | xargs egrep "SNAPSHOT|M[0-9]|RC[0-9]" | grep -v regex | wc -l)
                        if [ \$lines -eq 0 ]; then
                            set +x
                            ./mvnw clean deploy -Pspring -Dgpg.secretKeyring="\$${gpgSecRing()}" -Dgpg.publicKeyring="\$${
                gpgPubRing()}" -Dgpg.passphrase="\$${gpgPassphrase()}" -DSONATYPE_USER="\$${sonatypeUser()}" -DSONATYPE_PASSWORD="\$${sonatypePassword()}" -Pcentral -U
                            set -x
                        else
                            echo "Non release versions found. Aborting build"
                        fi
                    """
        }
        if (isRelease && releaseType != null && releaseType.equals("milestone")) {
            return """
					#!/bin/bash -x
			   		lines=\$(find . -type f -name pom.xml | xargs grep SNAPSHOT | wc -l)
					if [ \$lines -eq 0 ]; then
						./mvnw clean deploy -U -Pspring
					else
						echo "Snapshots found. Aborting the release build."
					fi
			   """
        }
        else {
            return  """
                        ./mvnw clean deploy -U
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