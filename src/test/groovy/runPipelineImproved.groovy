import com.passfailerror.ResultStackProcessor
import groovy.test.GroovyTestCase

import java.nio.file.Paths

class runPipelineImproved extends GroovyTestCase {

    Script pipelineScript
    ResultStackProcessor stackProcessor

    def steps = ["label", "echo", "sh"]
    def sections = ["pipeline", "agent", "stages", "stage", "steps"]


    def mockJenkins(pipelineScript, mockedSteps, mockedSections) {
        mockSteps(pipelineScript, mockedSteps)
        mockSections(pipelineScript, mockedSections)
    }

    def mockSections(pipelineScript, mockedSections) {
        mockedSections.each {
            section ->
                def currentSection = section
                pipelineScript.metaClass."$currentSection" = { Object... params ->
                    log.info(currentSection)
                    log.info(params[0]) // steps{closure}
                    if (params.length > 1) {
                        log.info(params[1]) // stage("name"){closure}
                    }
                }

        }
    }


    def mockSteps(pipelineScript, mockedSteps) {
        mockedSteps.each {
            step ->
                def currentStep = step
                pipelineScript.metaClass."$currentStep" = { Object... params ->
                    log.info(currentStep + " " + params[0].toString())
                }
        }
    }


    void setUp() {
        def pipelinePath = Paths.get("src/main/groovy/com/passfailerror/simplePipeline.groovy")
        def pipelineFile = pipelinePath.toFile()

        def binding = new Binding()
        GroovyShell shell = new GroovyShell(binding)
        pipelineScript = shell.parse(pipelineFile)
        mockJenkins(pipelineScript, steps, sections)

    }

    void testPipeline() {
        pipelineScript.run()
    }

}
