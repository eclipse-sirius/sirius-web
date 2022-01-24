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
package org.eclipse.sirius.web.spring.collaborative.editingcontext;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.web.spring.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.web.spring.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Parameters of the editing context event processor.
 *
 * @author sbegaudeau
 */
@Immutable
public final class EditingContextEventProcessorParameters {
    private ICollaborativeMessageService messageService;

    private IEditingContext editingContext;

    private IEditingContextPersistenceService editingContextPersistenceService;

    private ApplicationEventPublisher applicationEventPublisher;

    private List<IEditingContextEventHandler> editingContextEventHandlers;

    private IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private IDanglingRepresentationDeletionService danglingRepresentationDeletionService;

    private IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    private EditingContextEventProcessorParameters() {
        // Prevent instantiation
    }

    public ICollaborativeMessageService getMessageService() {
        return this.messageService;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public IEditingContextPersistenceService getEditingContextPersistenceService() {
        return this.editingContextPersistenceService;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return this.applicationEventPublisher;
    }

    public List<IEditingContextEventHandler> getEditingContextEventHandlers() {
        return this.editingContextEventHandlers;
    }

    public IRepresentationEventProcessorComposedFactory getRepresentationEventProcessorComposedFactory() {
        return this.representationEventProcessorComposedFactory;
    }

    public IDanglingRepresentationDeletionService getDanglingRepresentationDeletionService() {
        return this.danglingRepresentationDeletionService;
    }

    public IEditingContextEventProcessorExecutorServiceProvider getExecutorServiceProvider() {
        return this.executorServiceProvider;
    }

    public static Builder newEditingContextEventProcessorParameters() {
        return new Builder();
    }

    /**
     * The builder used to create the parameters.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private ICollaborativeMessageService messageService;

        private IEditingContext editingContext;

        private IEditingContextPersistenceService editingContextPersistenceService;

        private ApplicationEventPublisher applicationEventPublisher;

        private List<IEditingContextEventHandler> editingContextEventHandlers;

        private IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

        private IDanglingRepresentationDeletionService danglingRepresentationDeletionService;

        private IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

        private Builder() {
            // Prevent instantiation
        }

        public Builder messageService(ICollaborativeMessageService messageService) {
            this.messageService = Objects.requireNonNull(messageService);
            return this;
        }

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public Builder editingContextPersistenceService(IEditingContextPersistenceService editingContextPersistenceService) {
            this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
            return this;
        }

        public Builder applicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
            this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
            return this;
        }

        public Builder editingContextEventHandlers(List<IEditingContextEventHandler> editingContextEventHandlers) {
            this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
            return this;
        }

        public Builder representationEventProcessorComposedFactory(IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory) {
            this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
            return this;
        }

        public Builder danglingRepresentationDeletionService(IDanglingRepresentationDeletionService danglingRepresentationDeletionService) {
            this.danglingRepresentationDeletionService = Objects.requireNonNull(danglingRepresentationDeletionService);
            return this;
        }

        public Builder executorServiceProvider(IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider) {
            this.executorServiceProvider = Objects.requireNonNull(executorServiceProvider);
            return this;
        }

        public EditingContextEventProcessorParameters build() {
            EditingContextEventProcessorParameters parameters = new EditingContextEventProcessorParameters();
            parameters.messageService = Objects.requireNonNull(this.messageService);
            parameters.editingContext = Objects.requireNonNull(this.editingContext);
            parameters.editingContextPersistenceService = Objects.requireNonNull(this.editingContextPersistenceService);
            parameters.applicationEventPublisher = Objects.requireNonNull(this.applicationEventPublisher);
            parameters.editingContextEventHandlers = Objects.requireNonNull(this.editingContextEventHandlers);
            parameters.representationEventProcessorComposedFactory = Objects.requireNonNull(this.representationEventProcessorComposedFactory);
            parameters.danglingRepresentationDeletionService = Objects.requireNonNull(this.danglingRepresentationDeletionService);
            parameters.executorServiceProvider = Objects.requireNonNull(this.executorServiceProvider);
            return parameters;
        }
    }
}
