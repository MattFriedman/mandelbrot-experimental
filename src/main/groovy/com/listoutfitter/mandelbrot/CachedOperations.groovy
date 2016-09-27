package com.listoutfitter.mandelbrot

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class CachedOperations<T> implements Operations<T> {

    LoadingCache<T, T> squareCache = CacheBuilder.newBuilder().build(new CacheLoader<T, T>() {
        @Override
        T load(T key) throws Exception {
            key * key
        }
    })

    LoadingCache<MultiKey<T>, T> multipleCache = CacheBuilder.newBuilder().build(new CacheLoader<MultiKey<T>, T>() {
        @Override
        T load(MultiKey<T> key) throws Exception {
            key.v1 * key.v2
        }
    })

    @Override
    T square(T input) {

        input * input

//        squareCache.get(input)
    }

    @Override
    T mult(T x, T y) {

        x * y

//        def k = new MultiKey<T>()
//        k.v1 = x
//        k.v2 = y
//        multipleCache.get(k)
    }
}

class MultiKey<T> {
    T v1
    T v2

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        MultiKey multiKey = (MultiKey) o

        if (v1 != multiKey.v1) return false
        if (v2 != multiKey.v2) return false

        return true
    }

    int hashCode() {
        int result
        result = (v1 != null ? v1.hashCode() : 0)
        result = 31 * result + (v2 != null ? v2.hashCode() : 0)
        return result
    }
}
