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
package org.eclipse.sirius.web.spring.collaborative.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;

import reactor.core.publisher.Mono;

/**
 * Registry of all the editing context event handlers.
 *
 * @author sbegaudeau
 */
public interface IEditingContextEventProcessorRegistry {
    List<IEditingContextEventProcessor> getEditingContextEventProcessors();

    Mono<IPayload> dispatchEvent(String editingContextId, IInput input);

    Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(String editingContextId);

    void disposeEditingContextEventProcessor(String editingContextId);

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
        public Mono<IPayload> dispatchEvent(String editingContextId, IInput input) {
            return Mono.empty();
        }

        @Override
        public Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(String editingContextId) {
            return Optional.empty();
        }

        @Override
        public void disposeEditingContextEventProcessor(String editingContextId) {
        }

    }
}
