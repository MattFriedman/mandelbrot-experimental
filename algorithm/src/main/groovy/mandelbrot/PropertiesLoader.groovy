/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

/**

 * Created by Matt Friedman 2016-09-27
 */
class PropertiesLoader {

    Properties properties

    PropertiesLoader(String propertiesFilename) {
        properties = new Properties()
        def is = getClass().getResourceAsStream(propertiesFilename)
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
