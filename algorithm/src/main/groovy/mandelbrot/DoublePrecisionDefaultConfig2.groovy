package mandelbrot

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class DoublePrecisionDefaultConfig2 implements Config<Double> {

    final PropertiesLoader propertiesLoader

    DoublePrecisionDefaultConfig2(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader
    }

    @Override
    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations) {
        new DoublePrecisionMandelbrotAlgorithm(maxIterations)
    }

    @Override
    ToIntegerConverter<Double> toIntegerConverter() {
        new DoubleToIntegerConverter()
    }

    @Override
    Double one() {
        propertiesLoader.getDouble('one')
    }

    @Override
    int getPrecision() {
        Integer value = propertiesLoader.getInteger('precision')
        return value
    }

    @Override
    Double getWidth() {
        propertiesLoader.getDouble('width')
    }

    @Override
    Double getHeight() {
        getWidth()
    }

    @Override
    Double getRealStart() {
        -2D
    }

    @Override
    Double getRealEnd() {
        1D
    }

    @Override
    Double getImagStart() {
        -1.5D
    }

    @Override
    Double getImagEnd() {
        1.5D
    }
}
