grails.project.work.dir = "target"
grails.project.target.level = 1.6

grails.project.repos.default = "crm"

grails.project.dependency.resolution = {
    inherits("global") {}
    log "warn"
    legacyResolve false
    repositories {
        grailsHome()
        grailsCentral()
        mavenRepo "http://labs.technipelago.se/repo/crm-releases-local/"
        //mavenRepo "http://labs.technipelago.se/repo/plugin-releases-local/"
        mavenCentral()
    }
    dependencies {
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }
    plugins {
        build(":tomcat:$grailsVersion",
                ":release:2.2.1",
                ":rest-client-builder:1.0.3") {
            export = false
        }
        runtime ":hibernate:$grailsVersion"

        test(":spock:0.7") {
            export = false
            exclude "spock-grails-support"
        }
        test(":codenarc:0.19") { export = false }
        test(":code-coverage:1.2.7") { export = false }

        compile(":platform-core:1.0.RC5") { excludes 'resources' }

        compile "grails.crm:crm-core:latest.integration"
    }
}

codenarc {
    reports = {
        CrmXmlReport('xml') {
            outputFile = 'target/CodeNarcReport.xml'
            title = 'GR8 CRM CodeNarc Report'
        }
        CrmHtmlReport('html') {
            outputFile = 'target/CodeNarcReport.html'
            title = 'GR8 CRM CodeNarc Report'

        }
    }
    properties = {
        GrailsPublicControllerMethod.enabled = false
        CatchException.enabled = false
        CatchThrowable.enabled = false
        ThrowException.enabled = false
        ThrowRuntimeException.enabled = false
        GrailsStatelessService.enabled = false
        GrailsStatelessService.ignoreFieldNames = "dataSource,scope,sessionFactory,transactional,*Service,messageSource,grailsApplication,applicationContext,expose"
    }
    processTestUnit = false
    processTestIntegration = false
}

coverage {
    exclusions = ['**/radar/**']
}
