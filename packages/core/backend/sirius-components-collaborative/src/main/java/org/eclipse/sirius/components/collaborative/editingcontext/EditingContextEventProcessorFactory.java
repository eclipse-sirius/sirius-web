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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IChangeDescriptionListener;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutorFactory;
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

    private final IEditingContextExecutorFactory editingContextExecutorFactory;

    private final IChangeDescriptionListener changeDescriptionListener;

    public EditingContextEventProcessorFactory(ICollaborativeMessageService messageService,
            IRepresentationEventProcessorRegistryFactory representationEventProcessorRegistryFactory, IEditingContextExecutorFactory editingContextExecutorFactory,
            IChangeDescriptionListener changeDescriptionListener) {
        this.representationEventProcessorRegistryFactory = Objects.requireNonNull(representationEventProcessorRegistryFactory);
        this.editingContextExecutorFactory = Objects.requireNonNull(editingContextExecutorFactory);
        this.changeDescriptionListener = Objects.requireNonNull(changeDescriptionListener);
    }

    @Override
    public IEditingContextEventProcessor createEditingContextEventProcessor(IEditingContext editingContext) {
        var representationEventProcessorRegistry = this.representationEventProcessorRegistryFactory.createRepresentationEventProcessorRegistry();
        var editingContextExecutor = this.editingContextExecutorFactory.createEditingContextExecutor(editingContext, representationEventProcessorRegistry);
        return new EditingContextEventProcessor(representationEventProcessorRegistry, editingContextExecutor, editingContext, this.changeDescriptionListener);
    }

}
