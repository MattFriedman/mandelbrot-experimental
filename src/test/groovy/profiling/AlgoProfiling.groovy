package profiling

import com.listoutfitter.mandelbrot.DoublePrecisionMandelbrotAlgorithm
import spock.lang.Specification


/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class AlgoProfiling extends Specification {

    def 'profile'() {
        given:
            def algorithm = new DoublePrecisionMandelbrotAlgorithm(1000)
        when:

            1000.times {
                algorithm.compute(0,0)
            }

            def result
            profile {
                result = algorithm.compute(0,0)
            }.prettyPrint()
        then:
            println result

    }
}
