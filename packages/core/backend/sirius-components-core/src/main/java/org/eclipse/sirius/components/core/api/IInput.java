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
package org.eclipse.sirius.components.core.api;

import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;

/**
 * Interface implemented by all the inputs.
 *
 * <p>
 *     An input represents a request from the client to perform some operation on the system that may query its state or
 *     change it.
 *     Performing the operation will produce at least one {@link IPayload payload} which represents the result of the
 *     operations.
 *     Both the input and the resulting payload(s) share a common identifier allowing us to identify the payloads created
 *     by the input.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
public interface IInput extends ICause {
    /**
     * Returns the correlation identifier provided by the end user and used as the id of the various payloads and events
     * created from this input.
     *
     * @return The correlation identifier
     */
    UUID id();
}
