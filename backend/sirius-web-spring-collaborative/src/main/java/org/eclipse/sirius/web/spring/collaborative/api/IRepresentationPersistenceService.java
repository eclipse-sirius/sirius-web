/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.api;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;

/**
 * Used to persist the representations.
 *
 * @author sbegaudeau
 */
public interface IRepresentationPersistenceService {

    void save(IEditingContext editingContext, ISemanticRepresentationMetadata representationMetadata, IRepresentation representation);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements IRepresentationPersistenceService {

        @Override
        public void save(IEditingContext editingContext, ISemanticRepresentationMetadata representationMetadata, IRepresentation representation) {
            // Do nothing.
        }
    }
}
