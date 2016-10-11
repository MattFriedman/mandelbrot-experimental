package mandelbrot.consumer.points

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder
import mandelbrot.MandelbrotResult
import org.apache.commons.io.FileUtils
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-10-10
 */
@Component
class ResultFileWriter {

    @EqualsAndHashCode
    @Builder
   public  static class Meta {
        @JsonIgnore
        OutputStream outputStream

        int sequenceSize
        UUID correlationId
        int recordsWritten
        File tempFile
        int width
        int maxIterations
    }

    static Map<UUID, Meta> map = [:]

    ObjectMapper objectMapper = new ObjectMapper()


    @ServiceActivator
    void write(Message<List<MandelbrotResult>> message,
               @Header('maxIterations') int maxIterations,
               @Header('width') int width,
               @Header('sequenceSize') int sequenceSize,
               @Header('correlationId') UUID correlationId) {

        def tmpDir = new File(System.getProperty("java.io.tmpdir"))

        def list = message.payload

        def tempFile = File.createTempFile("$correlationId", ".writing", tmpDir)

        OutputStream outputStream

        if (map.get(correlationId)) {
            outputStream = map.get(correlationId).outputStream
        } else {

            outputStream = new BufferedOutputStream(new FileOutputStream(tempFile))
            int recordsWritten = 0
            map.put(
                    correlationId,
                    Meta.builder()
                            .correlationId(correlationId)
                            .outputStream(outputStream)
                            .sequenceSize(sequenceSize)
                            .recordsWritten(recordsWritten)
                            .tempFile(tempFile)
                            .width(width)
                            .maxIterations(maxIterations)
                            .build()
            )
        }

        def bytes = objectMapper.writeValueAsBytes(list)

        outputStream.write(bytes)
        outputStream.write('\n'.getBytes("UTF-8"))

        def meta = map.get(correlationId)
        meta.recordsWritten = ++meta.recordsWritten

        if( 0 == meta.recordsWritten % 10) {
            println "flushing output stream...."
            outputStream.flush()
        }

        println "seq size: ${meta.sequenceSize}    written: ${meta.recordsWritten}"


        def resultFilename = "$tmpDir/${correlationId}.mandelbrot-result"
        def finalResultFile = new File(resultFilename)
        def finalResultMetaFile = new File("$tmpDir/${correlationId}.mandelbrot-meta")

        if (meta.recordsWritten == meta.sequenceSize) {
            meta.outputStream.flush()
            meta.outputStream.close()
            FileUtils.moveFile(meta.tempFile, finalResultFile)
            objectMapper.writeValue(finalResultMetaFile, meta)

            println "Final file: ${finalResultFile}"
        }
    }
}
