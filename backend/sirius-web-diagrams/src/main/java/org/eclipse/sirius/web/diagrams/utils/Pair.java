/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.diagrams.utils;

import java.util.Objects;

/**
 * Represent an immutable key/value pair.
 *
 * @param <K>
 *            the key type.
 * @param <V>
 *            the value type.
 * @author fbarbin
 */
public class Pair<K, V> {

    private K key;

    private V value;

    /**
     * Default constructor.
     *
     * @param key
     *            the key. Cannot be null.
     * @param value
     *            the value. Cannot be null.
     */
    public Pair(K key, V value) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

}
