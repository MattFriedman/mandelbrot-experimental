/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.results.consumer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Charsets
import mandelbrot.MandelbrotResult
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageTypeSpecifier
import javax.imageio.metadata.IIOInvalidTreeException
import javax.imageio.metadata.IIOMetadata
import javax.imageio.metadata.IIOMetadataNode
import javax.imageio.stream.ImageOutputStream
import java.awt.*
import java.awt.image.BufferedImage
import java.util.List
import java.util.stream.IntStream

/**

 * Created by Matt Friedman 2016-10-10
 */
@Component
class ResultFileProcessingService {

    ObjectMapper objectMapper = new ObjectMapper()


    def typeRef = new TypeReference<List<MandelbrotResult>>() {}


    static class MandelbrotImageWriter {

        final double INCH_2_CM = 2.54
        final double DPI = 300

        void write(BufferedImage image) {

            def finalOutputFile = new File('/tmp/300-dpi-mandelbrot.png')

            // SEE: http://stackoverflow.com/a/4833697/1155079

            def iter = ImageIO.getImageWritersByFormatName('png')

            //noinspection ChangeToOperator
            def pngImageWriter = iter.next()

            def writeParam = pngImageWriter.getDefaultWriteParam()

            def typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB)

            def meta = pngImageWriter.getDefaultImageMetadata(typeSpecifier, writeParam)

            setDPI(meta)

            final ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(finalOutputFile)

            pngImageWriter.setOutput(imageOutputStream)

            pngImageWriter.write(meta, new IIOImage(image, null, meta), writeParam)

            imageOutputStream.close()
        }

        private void setDPI(IIOMetadata metadata) throws IIOInvalidTreeException {

            double dotsPerMilli = 1.0 * DPI / 10 / INCH_2_CM;

            IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
            horiz.setAttribute("value", Double.toString(dotsPerMilli));

            IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
            vert.setAttribute("value", Double.toString(dotsPerMilli));

            IIOMetadataNode dim = new IIOMetadataNode("Dimension");
            dim.appendChild(horiz);
            dim.appendChild(vert);

            IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
            root.appendChild(dim);

            metadata.mergeTree("javax_imageio_1.0", root);
        }

        public static void main(String[] args) {
            def imageWriter = new MandelbrotImageWriter()
            imageWriter.write(null)
        }
    }


    @ServiceActivator
    void process(Message<File> message) {


        int black = 0;



        def path = message.payload.absolutePath

        def metaFilename = path[0..path.lastIndexOf('.') - 1] + '.mandelbrot-meta'

        def meta = objectMapper.readValue(new File(metaFilename), ResultFileWriter.Meta)

        final int imgWidth = meta.width + 1
        final int imgHeight = imgWidth


        def colors = getColors(meta.maxIterations)

        def inputStream = new BufferedInputStream(new FileInputStream(message.payload))

        def it = IOUtils.lineIterator(inputStream, Charsets.UTF_8)

        def img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                List<MandelbrotResult> list = objectMapper.readValue(line, typeRef)

                list.parallelStream().forEach { result ->

                    final int color = result.inSet ? colors[result.totalIterations - 1] : black

                    if (result.inSet) {
                        def finalColor = Color.HSBtoRGB(result.hue, result.saturation, result.brightness);
                        img.setRGB(result.x, result.y, finalColor)
                    } else {
                        img.setRGB(result.x, result.y, 0)
                    }


                }
            }
        } finally {
            it.close();
            FileUtils.moveFile(message.payload, new File(path + '.done'))
        }

        // SEE: http://stackoverflow.com/a/4833697/1155079


        def imageWriter = new MandelbrotImageWriter()
        imageWriter.write(img)

//        ImageIO.write(img, "png", new File("/tmp/mandelbrot-ABCD.png"))

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
