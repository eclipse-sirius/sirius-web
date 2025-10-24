/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.editingcontext.api;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import reactor.core.publisher.Sinks;

/**
 * Use to get the existing representation event processor or create a new one.
 *
 * @author mcharfadi
 * @since v2025.10.0
 */
public interface IRepresentationEventProcessorProvider {

    Optional<IRepresentationEventProcessor> acquireRepresentationEventProcessor(ExecutorService executorService, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, String representationId, IInput input);

}
