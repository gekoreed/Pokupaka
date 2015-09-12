package com.pokupaka.processor.handlers;

//import jdk.nashorn.internal.ir.ObjectNode;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * By gekoreed on 9/12/15.
 */
public interface GeneralHandler<T extends Response> {
    T handle(ObjectNode node) throws Exception;
}
