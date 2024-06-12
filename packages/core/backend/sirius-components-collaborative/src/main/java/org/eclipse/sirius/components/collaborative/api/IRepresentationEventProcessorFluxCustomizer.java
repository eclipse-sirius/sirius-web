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

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Can be used to customize outputEvents of IRepresentationEventProcessor.
 *
 * @author mcharfadi
 */
public interface IRepresentationEventProcessorFluxCustomizer {

    boolean canHandle(String editingContextId, IRepresentationConfiguration configuration, IInput input, IRepresentationEventProcessor representationEventProcessor);
    
    Flux<IPayload> customize(String editingContextId, IRepresentationConfiguration configuration, IInput input, IRepresentationEventProcessor representationEventProcessor, Flux<IPayload> outputEvents);

}
