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

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Used to search and retrieve the metadata of a representation.
 *
 * @author sbegaudeau
 */
public interface IRepresentationMetadataSearchService {
    Optional<RepresentationMetadata> findByRepresentation(IRepresentation representation);

    List<RepresentationMetadata> findAllByTargetObjectId(IEditingContext editingContext, String targetObjectId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IRepresentationMetadataSearchService {

        @Override
        public Optional<RepresentationMetadata> findByRepresentation(IRepresentation representation) {
            return Optional.empty();
        }

        @Override
        public List<RepresentationMetadata> findAllByTargetObjectId(IEditingContext editingContext, String targetObjectId) {
            return List.of();
        }
    }
}
