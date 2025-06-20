/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Mono;

/**
 * Registry of all the editing context event processors.
 *
 * <p>
 *     This registry allows an application to manage all instances of the collaborative support for Sirius Components
 *     based workbenches.
 *     It can thus be used to find all the semantic data loaded in memory along with all the representations currently
 *     opened.
 *     This is one of the main entry point of Sirius Components and the support of the various collaborative features
 *     starts here.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.2
 */
public interface IEditingContextEventProcessorRegistry {
    /**
     * Used to retrieve an immutable list of editing context event processors currently used.
     *
     * <p>
     *     The result of this operation should not be kept as a reference for a long time.
     *     Instances of editing context event processor are managed by the registry and created or deleted frequently.
     * </p>
     *
     * @return All the editing context event processor currently used
     */
    List<IEditingContextEventProcessor> getEditingContextEventProcessors();

    /**
     * Used to send an {@link IInput}, describing some operation to be performed, to the editing context event processor
     * managing the editing context with the given identifier.
     *
     * @param editingContextId The identifier of the editing context
     * @param input The input describing the operation to be performed
     * @return A reactor mono used to received asynchronously one {@link IPayload} as a response for the given {@link IInput}
     */
    Mono<IPayload> dispatchEvent(String editingContextId, IInput input);

    /**
     * Used to retrieve the editing context event processor managing the editing context with the given identifier, if one
     * is being used it is returned, otherwise a new one is instantiated, added to this registry and returned.
     *
     * @param editingContextId The identifier of the editing context
     * @return An optional containing the editing context event processor used to manage the editing context if one exists
     * or can be created, an empty optional otherwise
     */
    Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(String editingContextId);

    /**
     * Disposes the editing context event processor managing the editing context with the given identifier.
     *
     * @param editingContextId The identifier of the editing context
     */
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
