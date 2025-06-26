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
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorChangeListener;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
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

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    private final MeterRegistry meterRegistry;

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IRepresentationEventProcessorChangeListener representationEventProcessorChangeListener;

    public EditingContextEventProcessorFactory(IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider, MeterRegistry meterRegistry, IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IRepresentationEventProcessorChangeListener representationEventProcessorChangeListener) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.executorServiceProvider = Objects.requireNonNull(executorServiceProvider);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
        this.representationEventProcessorChangeListener = Objects.requireNonNull(representationEventProcessorChangeListener);
    }

    @Override
    public IEditingContextEventProcessor createEditingContextEventProcessor(IEditingContext editingContext) {
        return new EditingContextEventProcessor(editingContext, this.executorServiceProvider, this.representationEventProcessorRegistry, this.representationEventProcessorChangeListener, this.meterRegistry);
    }

}
