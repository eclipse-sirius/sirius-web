/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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

import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.springframework.stereotype.Service;

/**
 * Used to create an {@link IEditingContextEventProcessor}.
 *
 * @author gcoutable
 */
@Service
public class EditingContextEventProcessorFactory implements IEditingContextEventProcessorFactory {

    private final IDanglingRepresentationDeletionService representationDeletionService;

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    private final MeterRegistry meterRegistry;

    public EditingContextEventProcessorFactory(IDanglingRepresentationDeletionService representationDeletionService, IRepresentationEventProcessorRegistry representationEventProcessorRegistry, EditingContextEventProcessorFactoryParameters parameters) {
        this.representationDeletionService = Objects.requireNonNull(representationDeletionService);
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.editingContextPersistenceService = parameters.getEditingContextPersistenceService();
        this.editingContextEventHandlers = parameters.getEditingContextEventHandlers();
        this.representationEventProcessorComposedFactory = parameters.getRepresentationEventProcessorComposedFactory();
        this.executorServiceProvider = parameters.getExecutorServiceProvider();
        this.inputPreProcessors = parameters.getInputPreProcessors();
        this.inputPostProcessors = parameters.getInputPostProcessors();
        this.meterRegistry = parameters.getMeterRegistry();
    }

    @Override
    public IEditingContextEventProcessor createEditingContextEventProcessor(IEditingContext editingContext) {
        var parameters = EditingContextEventProcessorParameters.newEditingContextEventProcessorParameters()
                .editingContext(editingContext)
                .danglingRepresentationDeletionService(this.representationDeletionService)
                .representationEventProcessorRegistry(this.representationEventProcessorRegistry)
                .editingContextPersistenceService(this.editingContextPersistenceService)
                .editingContextEventHandlers(this.editingContextEventHandlers)
                .representationEventProcessorComposedFactory(this.representationEventProcessorComposedFactory)
                .executorServiceProvider(this.executorServiceProvider)
                .inputPreProcessors(this.inputPreProcessors)
                .inputPostProcessors(this.inputPostProcessors)
                .meterRegistry(this.meterRegistry)
                .build();
        return new EditingContextEventProcessor(parameters);
    }

}
