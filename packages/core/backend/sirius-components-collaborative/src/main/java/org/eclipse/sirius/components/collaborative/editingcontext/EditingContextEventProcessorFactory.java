/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IChangeDescriptionListener;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutorFactory;
import org.eclipse.sirius.components.collaborative.api.IEditingContextManagerFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistryFactory;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to create an {@link IEditingContextEventProcessor}.
 *
 * @author gcoutable
 */
@Service
public class EditingContextEventProcessorFactory implements IEditingContextEventProcessorFactory {

    private final IRepresentationEventProcessorRegistryFactory representationEventProcessorRegistryFactory;

    private final IEditingContextManagerFactory editingContextManagerFactory;

    private final IEditingContextExecutorFactory editingContextExecutorFactory;

    private final List<IChangeDescriptionListener> changeDescriptionListeners;

    public EditingContextEventProcessorFactory(ICollaborativeMessageService messageService,
            IRepresentationEventProcessorRegistryFactory representationEventProcessorRegistryFactory, IEditingContextManagerFactory editingContextManagerFactory, IEditingContextExecutorFactory editingContextExecutorFactory,
            List<IChangeDescriptionListener> changeDescriptionListeners) {
        this.representationEventProcessorRegistryFactory = Objects.requireNonNull(representationEventProcessorRegistryFactory);
        this.editingContextManagerFactory = Objects.requireNonNull(editingContextManagerFactory);
        this.editingContextExecutorFactory = Objects.requireNonNull(editingContextExecutorFactory);
        this.changeDescriptionListeners = Objects.requireNonNull(changeDescriptionListeners);
    }

    @Override
    public IEditingContextEventProcessor createEditingContextEventProcessor(IEditingContext editingContext) {
        var representationEventProcessorRegistry = this.representationEventProcessorRegistryFactory.createRepresentationEventProcessorRegistry();
        var editingContextManager = this.editingContextManagerFactory.createEditingContextManager();
        var editingContextExecutor = this.editingContextExecutorFactory.createEditingContextExecutor(editingContext, representationEventProcessorRegistry);
        return new EditingContextEventProcessor(representationEventProcessorRegistry, editingContextManager, editingContextExecutor, editingContext, this.changeDescriptionListeners);
    }

}
