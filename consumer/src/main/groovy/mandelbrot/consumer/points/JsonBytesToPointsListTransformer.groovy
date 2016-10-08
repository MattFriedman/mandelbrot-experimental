package mandelbrot.consumer.points

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Point
import org.springframework.integration.annotation.Transformer
import org.springframework.stereotype.Component

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-07.
 */
@Component
class JsonBytesToPointsListTransformer {

    ObjectMapper objectMapper = new ObjectMapper()

    @Transformer
    List<Point> pointList(byte[] bytes) {
        def tr = new TypeReference<List<Point>>(){}
        def val =objectMapper.readValue(bytes, tr)
        val
    }

}
