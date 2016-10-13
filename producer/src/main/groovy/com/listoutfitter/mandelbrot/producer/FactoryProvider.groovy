/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

/**
 * Created by Matt Friedman on 2016-10-12.
 */
@Configuration
class FactoryProvider {

//    public <T extends Animal> T callFriend(String name, Class<T> type) {
//        return type.cast(friends.get(name));
//    }

    @Bean
    @Lazy
    public <T> Factory<T> factory(Class<T> clz, Long precision = null) {
        if( clz == Double) {

        } else {

        }

        println 'foo'
    }
}
