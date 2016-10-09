package mandelbrot.consumer.points

import mandelbrot.MandelbrotResult
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.stream.IntStream

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-07.
 */
@Component
class ImageService {

    int black = 0;

    @ServiceActivator
    void handleImageData(
            Message<List<List<MandelbrotResult>>> message,
            @Header('correlationId') correlationId,
            @Header('width') int width,
            @Header('maxIterations') int maxIterations
    ) {

        def colors = getColors(maxIterations)

        int imgWidth = width + 1
        int imgHeight = imgWidth

        def img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        message.payload.parallelStream().forEach { partition ->
            partition.parallelStream().forEach { result ->
                final int color = result.inSet ? colors[result.totalIterations - 1] : black
                img.setRGB(result.x, result.y, color)
            }
        }

        ImageIO.write(img, "png", new File("/tmp/mandelbrot-ABC.png"))
    }

    static int[] getColors(int maxIterations) {

        int[] colors = new int[maxIterations];

        IntStream.range(0, maxIterations).each { i ->
            float hue = i / 256f
            float saturation = 1f
            float brightness = i / (i + 8f)
            colors[i] = Color.HSBtoRGB(hue, saturation, brightness);
        }

        colors
    }
}
