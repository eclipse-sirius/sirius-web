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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistryFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * The factory that creates the representation event processor registry.
 *
 * @author gcoutable
 */
@Service
public class RepresentationEventProcessorRegistryFactory implements IRepresentationEventProcessorRegistryFactory {

    private final RepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final IDanglingRepresentationDeletionService danglingRepresentationDeletionService;

    private final MeterRegistry meterRegistry;

    public RepresentationEventProcessorRegistryFactory(RepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory,
            IDanglingRepresentationDeletionService danglingRepresentationDeletionService, MeterRegistry meterRegistry) {
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
        this.danglingRepresentationDeletionService = Objects.requireNonNull(danglingRepresentationDeletionService);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    public IRepresentationEventProcessorRegistry createRepresentationEventProcessorRegistry() {
        return new RepresentationEventProcessorRegistry(this.representationEventProcessorComposedFactory, this.danglingRepresentationDeletionService, this.meterRegistry);
    }
}
