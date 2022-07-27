/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.web.concurrent;

import java.util.Objects;
import java.util.concurrent.Callable;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Wraps a delegate {@link Callable} with logic for setting up a {@link RequestAttributes} before invoking the delegate
 * {@link Callable} and then removing the {@link RequestAttributes} after the delegate has completed.
 *
 * @param <V>
 *            the result type of method {@code call}
 * @author rpage
 */
public class DelegatingRequestContextCallable<V> implements Callable<V> {
    private final Callable<V> delegate;

    private final RequestAttributes delegateRequestContext;

    public DelegatingRequestContextCallable(Callable<V> delegate) {
        this.delegate = Objects.requireNonNull(delegate);
        this.delegateRequestContext = RequestContextHolder.getRequestAttributes();
    }

    @Override
    public V call() throws Exception {
        try {
            RequestContextHolder.setRequestAttributes(this.delegateRequestContext, true);
            return this.delegate.call();
        } finally {
            RequestContextHolder.resetRequestAttributes();
        }
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }
}
