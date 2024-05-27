/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.starter.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.springframework.context.support.MessageSourceAccessor;

import reactor.core.publisher.Mono;

/**
 *
 * Dispatch the given input for queries and mutations.
 *
 * @author sbegaudeau
 */
public class EditingContextDispatcher implements IEditingContextDispatcher {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final MessageSourceAccessor messageSourceAccessor;

    public EditingContextDispatcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, MessageSourceAccessor messageSourceAccessor) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public Mono<IPayload> dispatchQuery(String editingContextId, IInput input) {
        if (input instanceof IDiagramInput diagramInput) {
            var editingContextEventProcessor = this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(editingContextId);
            editingContextEventProcessor.ifPresent(iEditingContextEventProcessor -> iEditingContextEventProcessor.acquireRepresentationEventProcessor(new DiagramConfiguration(diagramInput.representationId()), diagramInput));
        }
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .defaultIfEmpty(new ErrorPayload(input.id(), this.messageSourceAccessor.getMessage("UNEXPECTED_ERROR")));
    }

    @Override
    public Mono<IPayload> dispatchMutation(String editingContextId, IInput input) {
        // @formatter:off
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .defaultIfEmpty(new ErrorPayload(input.id(), this.messageSourceAccessor.getMessage("UNEXPECTED_ERROR")));
        // @formatter:on
    }

}
