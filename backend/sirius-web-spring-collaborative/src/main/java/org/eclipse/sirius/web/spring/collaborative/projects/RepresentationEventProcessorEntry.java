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
package org.eclipse.sirius.web.spring.collaborative.projects;

import java.util.Objects;

import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessor;

import reactor.core.Disposable;

/**
 * Entry used to store the representation event processor along with additional information.
 *
 * @author sbegaudeau
 */
public class RepresentationEventProcessorEntry {
    private final IRepresentationEventProcessor representationEventProcessor;

    private final Disposable disposable;

    public RepresentationEventProcessorEntry(IRepresentationEventProcessor representationEventProcessor, Disposable disposable) {
        this.representationEventProcessor = Objects.requireNonNull(representationEventProcessor);
        this.disposable = Objects.requireNonNull(disposable);
    }

    public IRepresentationEventProcessor getRepresentationEventProcessor() {
        return this.representationEventProcessor;
    }

    public Disposable getDisposable() {
        return this.disposable;
    }

    public void dispose() {
        this.disposable.dispose();
        this.representationEventProcessor.dispose();
    }
}
