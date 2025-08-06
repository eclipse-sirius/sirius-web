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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import reactor.core.publisher.Sinks;

/**
 * Interface used to react to change descriptions emitted in the EditingContextEventProcessor.
 * <p>
 *     This interface differs from the {@link IChangeDescriptionListener} by providing some extensibility to the lifecycle
 *     of the EditingContextEventProcessor and by allowing downstream consumers to select when they want to React to
 *     change descriptions.
 * </p>
 *
 * @author sbegaudeau
 * @since v2025.10.0
 */
public interface IChangeDescriptionConsumer {
    default void preAccept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        // Do nothing
    }

    default void accept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        // Do nothing
    }

    default void postAccept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        // Do nothing
    }
}
