/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.events;

import java.util.UUID;

/**
 * The cause of a change in the system.
 *
 * <p>
 *     The cause is used to create a chain of causation between actions within the application.
 *     It allows application to figure out the root cause of various changes in order to react accordingly.
 *     Multiple changes in an application may be occurring thanks to different immediate cause but by following the chain
 *     of causation, we may be able to discover that they share the same root cause and thus consider them differently.
 * </p>
 *
 * @author sbegaudeau
 * @since v2024.11.0
 */
public interface ICause {
    /**
     * Provides the unique identifier of the cause of a change.
     *
     * <p>
     *     Being uniquely identifiable is the only requirement for a cause.
     *     This identifier will be used to allow applications to group changes together for specific operations when they
     *     have a common cause.
     * </p>
     *
     * @return The identifier of the cause of a change
     */
    UUID id();

    /**
     * Provides the direct parent cause of this cause.
     *
     * <p>
     *     One can use this method recursively to navigate the chain of causation of a change until no cause is found to
     *     identify the root cause of a change.
     * </p>
     *
     * @return The cause of the current cause
     */
    default ICause causedBy() {
        return null;
    }

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ICause {

        @Override
        public UUID id() {
            return null;
        }
    }
}