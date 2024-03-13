/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import org.eclipse.sirius.components.representations.ISemanticRepresentation;

/**
 * Used to persist the representations.
 *
 * @author sbegaudeau
 */
public interface IRepresentationPersistenceService {

    void save(ICause cause, IEditingContext editingContext, ISemanticRepresentation representation);

    /**
     * Empty implementation, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements IRepresentationPersistenceService {

        @Override
        public void save(ICause cause, IEditingContext editingContext, ISemanticRepresentation representation) {
            // Do nothing
        }

    }

}
