/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.collaborative.api.services;

import reactor.core.publisher.Mono;

/**
 * Interface used by a publisher that can be disposed.
 *
 * @author sbegaudeau
 */
public interface IDisposablePublisher {
    /**
     * Used to be notified that this instance can be disposed.
     *
     * @return A mono which can send a next event in order to indicate that it can be disposed. This mono can only emit
     *         a Boolean.TRUE value. When this publisher is disposed, it will still send a complete event which can be
     *         then be safely ignored.
     */
    Mono<Boolean> canBeDisposed();

    /**
     * Used to dispose this instance.
     */
    void dispose();
}
