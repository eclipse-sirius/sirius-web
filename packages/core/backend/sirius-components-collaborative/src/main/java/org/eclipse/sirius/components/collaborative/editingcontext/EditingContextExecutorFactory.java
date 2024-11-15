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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IComposedEditingContextEventHandlerFactory;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutorFactory;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * The factory used to create an editing context executor.
 *
 * @author gcoutable
 */
@Service
public class EditingContextExecutorFactory implements IEditingContextExecutorFactory {

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    private final ICollaborativeMessageService messageService;

    private final IComposedEditingContextEventHandlerFactory composedEditingContextEventHandlerFactory;

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    private final MeterRegistry meterRegistry;

    public EditingContextExecutorFactory(IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider, ICollaborativeMessageService messageService,
            IComposedEditingContextEventHandlerFactory composedEditingContextEventHandlerFactory, List<IInputPreProcessor> inputPreProcessors, List<IInputPostProcessor> inputPostProcessors, MeterRegistry meterRegistry) {
        this.executorServiceProvider = Objects.requireNonNull(executorServiceProvider);
        this.messageService = Objects.requireNonNull(messageService);
        this.composedEditingContextEventHandlerFactory = Objects.requireNonNull(composedEditingContextEventHandlerFactory);
        this.inputPreProcessors = Objects.requireNonNull(inputPreProcessors);
        this.inputPostProcessors = Objects.requireNonNull(inputPostProcessors);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    public IEditingContextExecutor createEditingContextExecutor(IEditingContext editingContext, IRepresentationEventProcessorRegistry representationEventProcessorRegistry) {
        var composedEditingContextEventHandler = this.composedEditingContextEventHandlerFactory.createComposedEditingContextEventHandler(representationEventProcessorRegistry);
        return new EditingContextExecutor(editingContext,
                this.executorServiceProvider, composedEditingContextEventHandler, this.messageService, this.inputPreProcessors, this.inputPostProcessors, this.meterRegistry
        );
    }
}
