package mandelbrot
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-11.
 */
class DoublePrecisionMandelbrotAlgorithm extends GenericMandelbrotAlgorithm<Double> {

    DoublePrecisionMandelbrotAlgorithm(final int maxIterations) {
        super(maxIterations)
    }

    @Override
    Double two() {
        2D
    }

    @Override
    Double zero() {
        0D
    }

    @Override
    Double four() {
        4D
    }
}
