/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.Optional;

/**
 * Interface of the delegation service interacting with domain objects.
 *
 * @author arichard
 */
public interface IObjectSearchServiceDelegate {

    boolean canHandle(IEditingContext editingContext, String objectId);

    Optional<Object> getObject(IEditingContext editingContext, String objectId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IObjectSearchServiceDelegate {
        @Override
        public boolean canHandle(IEditingContext editingContext, String objectId) {
            return true;
        }

        @Override
        public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
            return Optional.empty();
        }

    }
}
