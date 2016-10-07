package com.listoutfitter.mandelbrot.producer

import org.springframework.messaging.Message

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
class Receiver {

    void receiveMessage(Message message) {
        throw new RuntimeException(message)
    }
}
