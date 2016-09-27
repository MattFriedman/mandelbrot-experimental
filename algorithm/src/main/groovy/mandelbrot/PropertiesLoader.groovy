package mandelbrot

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-27.
 */
class PropertiesLoader {

    Properties properties

    PropertiesLoader() {
        properties = new Properties()
        def is = getClass().getResourceAsStream('/mandelbrot.properties')
        properties.load(is)
    }

    Integer getInteger(def name) {
        Integer.valueOf(properties.get(name) as String)
    }

    Double getDouble(def name) {
        Double.valueOf(properties.get(name) as String)
    }

    String getString(def name) {
        properties.get(name) as String
    }



}
