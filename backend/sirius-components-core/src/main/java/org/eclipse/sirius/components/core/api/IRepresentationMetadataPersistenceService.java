/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.sirius.components.core.RepresentationMetadata;

/**
 * Used to persist representation metadata.
 *
 * @author sbegaudeau
 */
public interface IRepresentationMetadataPersistenceService {

    void save(IEditingContext editingContext, RepresentationMetadata representationMetadata);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IRepresentationMetadataPersistenceService {

        @Override
        public void save(IEditingContext editingContext, RepresentationMetadata representationMetadata) {
            // Do nothing
        }

    }

}
