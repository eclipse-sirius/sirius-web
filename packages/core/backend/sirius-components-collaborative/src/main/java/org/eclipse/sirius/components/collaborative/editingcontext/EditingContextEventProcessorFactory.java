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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Used to create an {@link IEditingContextEventProcessor}.
 *
 * @author gcoutable
 */
@Service
public class EditingContextEventProcessorFactory implements IEditingContextEventProcessorFactory {

    private final ICollaborativeMessageService messageService;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final IDanglingRepresentationDeletionService representationDeletionService;

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    public EditingContextEventProcessorFactory(ICollaborativeMessageService messageService, IEditingContextPersistenceService editingContextPersistenceService,
            ApplicationEventPublisher applicationEventPublisher, List<IEditingContextEventHandler> editingContextEventHandlers,
            IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory, IDanglingRepresentationDeletionService representationDeletionService,
            IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider) {
        this.messageService = Objects.requireNonNull(messageService);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
        this.representationDeletionService = Objects.requireNonNull(representationDeletionService);
        this.executorServiceProvider = Objects.requireNonNull(executorServiceProvider);
    }

    @Override
    public IEditingContextEventProcessor createEditingContextEventProcessor(IEditingContext editingContext) {
        // @formatter:off
        var parameters = EditingContextEventProcessorParameters.newEditingContextEventProcessorParameters()
                .messageService(this.messageService)
                .editingContext(editingContext)
                .editingContextPersistenceService(this.editingContextPersistenceService)
                .applicationEventPublisher(this.applicationEventPublisher)
                .editingContextEventHandlers(this.editingContextEventHandlers)
                .representationEventProcessorComposedFactory(this.representationEventProcessorComposedFactory)
                .danglingRepresentationDeletionService(this.representationDeletionService)
                .executorServiceProvider(this.executorServiceProvider)
                .build();
        // @formatter:on
        return new EditingContextEventProcessor(parameters);
    }

}
