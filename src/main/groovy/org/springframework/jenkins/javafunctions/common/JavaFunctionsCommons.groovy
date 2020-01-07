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

    String cleanAndDeploy(boolean isGaRelease) {
        return isGaRelease ?
                """
                        lines=\$(find . -type f -name pom.xml | xargs egrep "SNAPSHOT|M[0-9]|RC[0-9]" | wc -l)
                        if [ \$lines -eq 0 ]; then
                            set +x
                            ./mvnw clean deploy -Dgpg.secretKeyring="\$${gpgSecRing()}" -Dgpg.publicKeyring="\\\$${
                    gpgPubRing()
                }" -Dgpg.passphrase="\\\$${gpgPassphrase()}" -DSONATYPE_USER="\$${sonatypeUser()}" -DSONATYPE_PASSWORD="\$${sonatypePassword()}" -Pcentral -U
                            set -x
                        else
                            echo "Non release versions found. Aborting build"
                        fi
                    """ :
                """
                        ./mvnw clean deploy -U
                    """
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