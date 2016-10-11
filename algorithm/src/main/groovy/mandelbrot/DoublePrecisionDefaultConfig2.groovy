package mandelbrot

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-09-12
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
        propertiesLoader.getDouble('real.start')
    }

    @Override
    Double getRealEnd() {
        propertiesLoader.getDouble('real.end')
    }

    @Override
    Double getImagStart() {
        propertiesLoader.getDouble('imaginary.start')
    }

    @Override
    Double getImagEnd() {
        propertiesLoader.getDouble('imaginary.end')
    }
}
