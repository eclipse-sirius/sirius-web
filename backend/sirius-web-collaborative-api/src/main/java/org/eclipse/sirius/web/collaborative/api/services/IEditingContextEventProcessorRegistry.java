/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;

/**
 * Registry of all the editing context event handlers.
 *
 * @author sbegaudeau
 */
public interface IEditingContextEventProcessorRegistry {
    List<IEditingContextEventProcessor> getEditingContextEventProcessors();

    Optional<IPayload> dispatchEvent(UUID editingContextId, IInput input);

    Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(UUID editingContextId);

    void disposeEditingContextEventProcessor(UUID editingContextId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEditingContextEventProcessorRegistry {

        @Override
        public List<IEditingContextEventProcessor> getEditingContextEventProcessors() {
            return List.of();
        }

        @Override
        public Optional<IPayload> dispatchEvent(UUID editingContextId, IInput input) {
            return Optional.empty();
        }

        @Override
        public Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(UUID editingContextId) {
            return Optional.empty();
        }

        @Override
        public void disposeEditingContextEventProcessor(UUID editingContextId) {
        }

    }
}
