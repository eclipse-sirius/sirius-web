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

/**
 * Interface to be implemented by all payloads.
 *
 * <p>
 *     A payload is a response to an input using the same identifier as the input.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
public interface IPayload {
    /**
     * Returns the correlation identifier from the IInput which is responsible for the creation of this payload.
     *
     * @return The correlation identifier
     */
    UUID id();
}
