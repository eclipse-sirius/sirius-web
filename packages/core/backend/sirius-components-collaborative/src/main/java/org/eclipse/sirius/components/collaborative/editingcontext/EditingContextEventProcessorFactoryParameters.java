/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.springframework.stereotype.Service;

/**
 * Bundles the bean dependencies that {@link EditingContextEventProcessorFactory} needs into a single object for convenience.
 *
 * @author frouene
 */
@Service
public class EditingContextEventProcessorFactoryParameters {


    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    public EditingContextEventProcessorFactoryParameters(IEditingContextPersistenceService editingContextPersistenceService, List<IEditingContextEventHandler> editingContextEventHandlers,
            IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory, IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider,
            List<IInputPreProcessor> inputPreProcessors, List<IInputPostProcessor> inputPostProcessors) {
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
        this.executorServiceProvider = Objects.requireNonNull(executorServiceProvider);
        this.inputPreProcessors = Objects.requireNonNull(inputPreProcessors);
        this.inputPostProcessors = Objects.requireNonNull(inputPostProcessors);
    }

    public IEditingContextPersistenceService getEditingContextPersistenceService() {
        return this.editingContextPersistenceService;
    }

    public List<IEditingContextEventHandler> getEditingContextEventHandlers() {
        return this.editingContextEventHandlers;
    }

    public IRepresentationEventProcessorComposedFactory getRepresentationEventProcessorComposedFactory() {
        return this.representationEventProcessorComposedFactory;
    }

    public IEditingContextEventProcessorExecutorServiceProvider getExecutorServiceProvider() {
        return this.executorServiceProvider;
    }

    public List<IInputPreProcessor> getInputPreProcessors() {
        return this.inputPreProcessors;
    }

    public List<IInputPostProcessor> getInputPostProcessors() {
        return this.inputPostProcessors;
    }
}
