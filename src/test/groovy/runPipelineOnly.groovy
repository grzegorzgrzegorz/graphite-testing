import groovy.test.GroovyTestCase
import java.nio.file.Paths

class runPipelineOnly extends GroovyTestCase {


    Script pipelineScript


    def mockJenkins(pipelineScript) {
        pipelineScript.metaClass.pipeline = { Object... params ->
            log.info("pipeline")
            log.info(params[0])
        }
        pipelineScript.metaClass.agent = { Object... params ->
            log.info("agent")
            log.info(params[0])
        }

        pipelineScript.metaClass.label = { Object... params ->
            log.info("label "+params[0].toString())
        }

        pipelineScript.metaClass.stages = { Object... params ->
            log.info("stages")
            log.info(params[0])
        }

        pipelineScript.metaClass.stage = { Object... params ->
            log.info("stage("+params[0].toString()+")")
            log.info(params[1])
        }

        pipelineScript.metaClass.steps = { Object... params ->
            log.info("steps")
            log.info(params[0])
        }

        pipelineScript.metaClass.echo = { Object... params ->
            log.info("echo")
            log.info(params[0])}

        pipelineScript.metaClass.sh = { Object... params ->
            log.info("sh")
            log.info(params[0])}
    }

    void setUp() {
        def pipelineFile = Paths.get("src/main/groovy/com/passfailerror/simplePipeline.groovy").toFile()
        def binding = new Binding()
        GroovyShell shell = new GroovyShell(binding)
        pipelineScript = shell.parse(pipelineFile)
        mockJenkins(pipelineScript)
    }

    void testPipeline() {
        pipelineScript.run()
    }

}
