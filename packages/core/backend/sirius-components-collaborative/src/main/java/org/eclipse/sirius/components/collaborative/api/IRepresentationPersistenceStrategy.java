/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Common interface used to define a persistence strategy.
 *
 * @author fbarbin
 */
public interface IRepresentationPersistenceStrategy {
    void applyPersistenceStrategy(ICause cause, IEditingContext editingContext, IRepresentation representation);

    /**
     * Empty implementation, used for mocks in unit tests.
     *
     * @author fbarbin
     */
    class NoOp implements IRepresentationPersistenceStrategy {

        @Override
        public void applyPersistenceStrategy(ICause cause, IEditingContext editingContext, IRepresentation representation) {
            // Do nothing
        }

    }
}
