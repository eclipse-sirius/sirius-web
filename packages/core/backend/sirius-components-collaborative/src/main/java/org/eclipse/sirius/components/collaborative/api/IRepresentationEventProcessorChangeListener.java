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
package org.eclipse.sirius.components.collaborative.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Sinks;

import java.util.concurrent.ExecutorService;

/**
 * Used to react to onChange events from event processors.
 *
 * @author mcharfadi
 */
public interface IRepresentationEventProcessorChangeListener {

    void onChange(ExecutorService executorService, Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ChangeDescription changeDescription);

}
