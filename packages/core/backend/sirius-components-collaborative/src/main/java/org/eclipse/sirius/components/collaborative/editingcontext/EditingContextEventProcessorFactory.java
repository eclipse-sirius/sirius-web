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
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistryFactory;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Used to create an {@link IEditingContextEventProcessor}.
 *
 * @author gcoutable
 */
@Service
public class EditingContextEventProcessorFactory implements IEditingContextEventProcessorFactory {

    private final ICollaborativeMessageService messageService;

    private final IRepresentationEventProcessorRegistryFactory representationEventProcessorRegistryFactory;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    private final IChangeDescriptionListener changeDescriptionListener;

    private final MeterRegistry meterRegistry;

    public EditingContextEventProcessorFactory(ICollaborativeMessageService messageService,
            IRepresentationEventProcessorRegistryFactory representationEventProcessorRegistryFactory,
            IChangeDescriptionListener changeDescriptionListener,
            EditingContextEventProcessorFactoryParameters parameters) {
        this.messageService = Objects.requireNonNull(messageService);
        this.representationEventProcessorRegistryFactory = Objects.requireNonNull(representationEventProcessorRegistryFactory);
        this.changeDescriptionListener = Objects.requireNonNull(changeDescriptionListener);
        this.editingContextEventHandlers = parameters.getEditingContextEventHandlers();
        this.executorServiceProvider = parameters.getExecutorServiceProvider();
        this.inputPreProcessors = parameters.getInputPreProcessors();
        this.inputPostProcessors = parameters.getInputPostProcessors();
        this.meterRegistry = parameters.getMeterRegistry();
    }

    @Override
    public IEditingContextEventProcessor createEditingContextEventProcessor(IEditingContext editingContext) {
        var parameters = EditingContextEventProcessorParameters.newEditingContextEventProcessorParameters()
                .messageService(this.messageService)
                .editingContext(editingContext)
                .changeDescriptionListener(this.changeDescriptionListener)
                .editingContextEventHandlers(this.editingContextEventHandlers)
                .executorServiceProvider(this.executorServiceProvider)
                .inputPreProcessors(this.inputPreProcessors)
                .inputPostProcessors(this.inputPostProcessors)
                .meterRegistry(this.meterRegistry)
                .build();
        return new EditingContextEventProcessor(this.representationEventProcessorRegistryFactory.createRepresentationEventProcessorRegistry(), parameters);
    }

}
