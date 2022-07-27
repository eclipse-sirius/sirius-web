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

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Wraps a delegate {@link Runnable} with logic for setting up a {@link RequestAttributes} before invoking the delegate
 * {@link Runnable} and then removing the {@link RequestAttributes} after the delegate has completed.
 *
 * @author rpage
 */
public class DelegatingRequestContextRunnable implements Runnable {
    private final Runnable delegate;

    private final RequestAttributes delegateRequestContext;

    public DelegatingRequestContextRunnable(Runnable delegate) {
        this.delegate = Objects.requireNonNull(delegate);
        this.delegateRequestContext = RequestContextHolder.getRequestAttributes();
    }

    @Override
    public void run() {
        try {
            RequestContextHolder.setRequestAttributes(this.delegateRequestContext, true);
            this.delegate.run();
        } finally {
            RequestContextHolder.resetRequestAttributes();
        }
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }
}
