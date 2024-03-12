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
package org.eclipse.sirius.components.events;

import java.util.UUID;

/**
 * The cause of a change in the system.
 *
 * @author sbegaudeau
 */
public interface ICause {
    UUID id();

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