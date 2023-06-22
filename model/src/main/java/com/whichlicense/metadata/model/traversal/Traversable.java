/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model.traversal;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.whichlicense.metadata.model.traversal.TraversalOrder.ANY;
import static java.util.Spliterator.ORDERED;

public interface Traversable<E> extends Iterable<E> {
    default Traversal<E> traverse() {
        return traverse(ANY);
    }

    Traversal<E> traverse(TraversalOrder order);

    default Stream<E> streamTraversal() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<E> streamTraversal(TraversalOrder order) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(traverse(order), ORDERED), false);
    }

    @Override
    default Iterator<E> iterator() {
        return traverse();
    }
}
