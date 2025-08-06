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
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IChangeDescriptionListener;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to create an {@link IEditingContextEventProcessor}.
 *
 * @author gcoutable
 */
@Service
public class EditingContextEventProcessorFactory implements IEditingContextEventProcessorFactory {

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final IChangeDescriptionListener changeDescriptionListener;

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    private final MeterRegistry meterRegistry;

    public EditingContextEventProcessorFactory(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, EditingContextEventProcessorFactoryParameters parameters, IChangeDescriptionListener changeDescriptionListener) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.editingContextEventHandlers = parameters.getEditingContextEventHandlers();
        this.representationEventProcessorComposedFactory = parameters.getRepresentationEventProcessorComposedFactory();
        this.changeDescriptionListener = Objects.requireNonNull(changeDescriptionListener);
        this.executorServiceProvider = parameters.getExecutorServiceProvider();
        this.inputPreProcessors = parameters.getInputPreProcessors();
        this.inputPostProcessors = parameters.getInputPostProcessors();
        this.meterRegistry = parameters.getMeterRegistry();
    }

    @Override
    public IEditingContextEventProcessor createEditingContextEventProcessor(IEditingContext editingContext) {
        var parameters = EditingContextEventProcessorParameters.newEditingContextEventProcessorParameters()
                .editingContext(editingContext)
                .representationEventProcessorRegistry(this.representationEventProcessorRegistry)
                .editingContextEventHandlers(this.editingContextEventHandlers)
                .representationEventProcessorComposedFactory(this.representationEventProcessorComposedFactory)
                .changeDescriptionListener(this.changeDescriptionListener)
                .executorServiceProvider(this.executorServiceProvider)
                .inputPreProcessors(this.inputPreProcessors)
                .inputPostProcessors(this.inputPostProcessors)
                .meterRegistry(this.meterRegistry)
                .build();
        return new EditingContextEventProcessor(parameters);
    }

}
