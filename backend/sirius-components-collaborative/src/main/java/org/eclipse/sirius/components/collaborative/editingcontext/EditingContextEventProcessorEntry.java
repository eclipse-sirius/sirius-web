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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;

import reactor.core.Disposable;

/**
 * Entry used to store the editing context event processor along with additional information.
 *
 * @author sbegaudeau
 */
public class EditingContextEventProcessorEntry {
    private final IEditingContextEventProcessor editingContextEventProcessor;

    private final Disposable disposable;

    public EditingContextEventProcessorEntry(IEditingContextEventProcessor editingContextEventProcessor, Disposable disposable) {
        this.editingContextEventProcessor = Objects.requireNonNull(editingContextEventProcessor);
        this.disposable = Objects.requireNonNull(disposable);
    }

    public IEditingContextEventProcessor getEditingContextEventProcessor() {
        return this.editingContextEventProcessor;
    }

    public Disposable getDisposable() {
        return this.disposable;
    }

    public void dispose() {
        this.disposable.dispose();
        this.editingContextEventProcessor.dispose();
    }
}
