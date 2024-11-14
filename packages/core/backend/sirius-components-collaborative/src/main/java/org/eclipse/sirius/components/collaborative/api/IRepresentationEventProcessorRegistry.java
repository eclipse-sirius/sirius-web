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
package org.eclipse.sirius.components.collaborative.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Sinks;

/**
 * The registry of representation event processor held by an editing context.
 *
 * @author gcoutable
 */
public interface IRepresentationEventProcessorRegistry {

    String REPRESENTATION_ID = "representationId";

    String REPRESENTATION_LABEL = "representationLabel";

    Optional<IRepresentationEventProcessor> getOrCreateRepresentationEventProcessor(String representationId, IEditingContext editingContext, Sinks.Many<Boolean> canBeDisposedSink,
            IEditingContextExecutor executorService);

    List<IRepresentationEventProcessor> values();

    void onChange(ChangeDescription changeDescription, IEditingContext editingContext, Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink);

    void dispose();
}
