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

import java.util.Objects;

import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorChangeListener;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorGetter;
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

    private final IEditingContextEventProcessorHandler editingContextEventProcessorHandler;

    private final IRepresentationEventProcessorChangeListener representationEventProcessorChangeListener;

    private final IRepresentationEventProcessorGetter representationEventProcessorGetter;

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    private final MeterRegistry meterRegistry;

    public EditingContextEventProcessorFactory(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IEditingContextEventProcessorHandler editingContextEventProcessorHandler, EditingContextEventProcessorFactoryParameters parameters, IRepresentationEventProcessorChangeListener representationEventProcessorChangeListener, IRepresentationEventProcessorGetter representationEventProcessorGetter) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.editingContextEventProcessorHandler = Objects.requireNonNull(editingContextEventProcessorHandler);
        this.representationEventProcessorGetter = Objects.requireNonNull(representationEventProcessorGetter);
        this.representationEventProcessorChangeListener = Objects.requireNonNull(representationEventProcessorChangeListener);
        this.executorServiceProvider = parameters.getExecutorServiceProvider();
        this.meterRegistry = parameters.getMeterRegistry();
    }

    @Override
    public IEditingContextEventProcessor createEditingContextEventProcessor(IEditingContext editingContext) {
        return new EditingContextEventProcessor(this.executorServiceProvider, editingContext, this.representationEventProcessorRegistry, this.representationEventProcessorChangeListener, this.editingContextEventProcessorHandler, this.representationEventProcessorGetter, this.meterRegistry);
    }

}
