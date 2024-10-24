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
package org.eclipse.sirius.components.collaborative.api;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;

/**
 * Used to persist representation metadata.
 *
 * @author gcoutable
 */
public interface IRepresentationMetadataPersistenceService {

    void save(ICause cause, IEditingContext editingContext, RepresentationMetadata representationMetadata, String targetObjectId);

    /**
     * Empty implementation, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements IRepresentationMetadataPersistenceService {

        @Override
        public void save(ICause cause, IEditingContext editingContext, RepresentationMetadata representationMetadata, String targetObjectId) {
            // Do nothing
        }

    }
}
