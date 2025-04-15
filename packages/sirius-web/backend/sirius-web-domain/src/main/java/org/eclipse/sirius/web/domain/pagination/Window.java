/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.web.domain.pagination;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.springframework.data.domain.ScrollPosition;

/**
 * Custom Window with additional hasPrevious method.
 *
 * @param <T> The type containing by the window.
 *
 * @author arichard
 */
public class Window<T> implements org.springframework.data.domain.Window<T> {

    private final org.springframework.data.domain.Window<T> delegate;
    
    private final boolean hasPrevious;

    public Window(List<T> items, IntFunction<? extends ScrollPosition> positionFunction, boolean hasNext, boolean hasPrevious) {
        this.delegate = org.springframework.data.domain.Window.from(items, positionFunction, hasNext);
        this.hasPrevious = hasPrevious;
    }

    public Window(org.springframework.data.domain.Window<T> delegate, boolean hasPrevious) {
        this.delegate = Objects.requireNonNull(delegate);
        this.hasPrevious = hasPrevious;
    }

    @Override
    public Iterator<T> iterator() {
        return this.delegate.iterator();
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public List<T> getContent() {
        return this.delegate.getContent();
    }

    @Override
    public boolean hasNext() {
        return this.delegate.hasNext();
    }

    @Override
    public ScrollPosition positionAt(int index) {
        return this.delegate.positionAt(index);
    }

    @Override
    public <U> org.springframework.data.domain.Window<U> map(Function<? super T, ? extends U> converter) {
        return this.delegate.map(converter);
    }

    public boolean hasPrevious() {
        return this.hasPrevious;
    }
}
