/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFluxCustomizer;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to add new payloads to a representation event processor during tests.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnCustomizedRepresentationEventProcessor.class)
public class TestRepresentationEventProcessorFluxCustomizer implements IRepresentationEventProcessorFluxCustomizer {
    @Override
    public boolean canHandle(String editingContextId, IRepresentationConfiguration configuration, IInput input, IRepresentationEventProcessor representationEventProcessor) {
        return true;
    }

    @Override
    public Flux<IPayload> customize(String editingContextId, IRepresentationConfiguration configuration, IInput input, IRepresentationEventProcessor representationEventProcessor, Flux<IPayload> outputEvents) {
        return Flux.merge(
                outputEvents,
                Flux.just(new TestPayload(input.id()))
        );
    }
}
