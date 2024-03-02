/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.starter;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

import reactor.core.publisher.Mono;

/**
 *
 * Dispatch the given input for queries and mutations.
 *
 * @author sbegaudeau
 */
public class EditingContextDispatcher implements IEditingContextDispatcher {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IMessageService messageService;

    public EditingContextDispatcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IMessageService messageService) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public Mono<IPayload> dispatchQuery(String editingContextId, IInput input) {
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .defaultIfEmpty(new ErrorPayload(input.id(), this.messageService.unexpectedError()));
    }

    @Override
    public Mono<IPayload> dispatchMutation(String editingContextId, IInput input) {
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .defaultIfEmpty(new ErrorPayload(input.id(), this.messageService.unexpectedError()));
    }
}
