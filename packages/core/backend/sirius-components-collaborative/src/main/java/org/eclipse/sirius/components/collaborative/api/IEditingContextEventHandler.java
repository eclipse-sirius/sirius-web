/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Service used to handle input to query or modify the data accessible thanks to the {@link IEditingContext}.
 *
 * <p>
 *     Given that the editing context is isolated to prevent concurrent modifications outside the collaborative support,
 *     the proper way to manipulate data from the editing context is to create an {@link IEditingContextEventHandler} and
 *     a dedicated {@link IInput}.
 * </p>
 *
 * <p>
 *     The input will describe the operation to be performed and can then be sent to the {@link IEditingContextEventProcessor}.
 *     Once received by the editing context event processor, the input will be handled by the first editing context event
 *     handler that can handle it.
 *     By creating a custom event handler, you can thus receive your input and use its parameter to perform some operation
 *     with the editing context.
 * </p>
 *
 * <p>
 *     Such a service combined with a dedicated input and sometime a dedicated payload (to return custom data) is the main
 *     way to manipulate the editing context.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.2
 */
public interface IEditingContextEventHandler {

    /**
     * Used to indicate if the event handler should be used for the given input.
     *
     * @param editingContext The editing context used to access to the semantic data
     * @param input The input describing the operation to be performed
     * @return <code>true</code> is the event handler can be used for the given input, <code>false</code> otherwise
     */
    boolean canHandle(IEditingContext editingContext, IInput input);

    /**
     * Used to perform the operation described by the given input.
     *
     * @param payloadSink A reactor sink which must be used to send back asynchronously one {@link IPayload} as the answer
     *                    to the given {@link IInput}. Sending back an answer is compulsory. It can be an error or a success
     *                    or any kind of custom payload containing the result of the operation performed. The identifier
     *                    of the payload returned is a correlation identifier and must thus be the same as the identifier
     *                    of the given input in order to track changes in the system
     * @param changeDescriptionSink A reactor sink which can be used to send back descriptions of the changes made to the
     *                              system. Those change descriptions are then used by other parts of the application to
     *                              react to those changes. For example, by emitting a change description indicating that
     *                              some semantic data may have been modified, one can trigger the refresh of all opened
     *                              representations. If nothing has been done, no change description needs to be sent.
     * @param editingContext The editing context used to access the semantic data
     * @param input The input describing the operation to be performed
     */
    void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input);
}
