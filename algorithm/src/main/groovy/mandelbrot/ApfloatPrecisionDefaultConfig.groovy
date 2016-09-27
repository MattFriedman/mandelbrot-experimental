package mandelbrot

import org.apfloat.Apfloat

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class ApfloatPrecisionDefaultConfig implements Config<Apfloat> {

    private final target = new DoublePrecisionDefaultConfig()

    final int precision
    ApfloatPrecisionDefaultConfig(int precision) {
        this.precision = precision
    }

    @Override
    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations) {
        new ApfloatPrecisionMandelbrotAlgorithm(maxIterations, precision)
    }

    @Override
    ToIntegerConverter<Apfloat> toIntegerConverter() {
        new ApfloatToIntegerConverter()
    }

    @Override
    Apfloat one() {
        new Apfloat(1, precision)
    }

    @Override
    int getPrecision() {
        return precision
    }

    @Override
    Apfloat getWidth() {
        new Apfloat(target.width, precision)
    }

    @Override
    Apfloat getHeight() {
        new Apfloat(target.height, precision)
    }

    @Override
    Apfloat getRealStart() {
        new Apfloat(target.realStart, precision)
    }

    @Override
    Apfloat getRealEnd() {
        new Apfloat(target.realEnd, precision)
    }

    @Override
    Apfloat getImagStart() {
        new Apfloat(target.imagStart, precision)
    }

    @Override
    Apfloat getImagEnd() {
        new Apfloat(target.imagEnd, precision)
    }
}
