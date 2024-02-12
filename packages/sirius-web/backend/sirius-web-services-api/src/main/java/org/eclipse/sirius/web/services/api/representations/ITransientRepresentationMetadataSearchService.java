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
package org.eclipse.sirius.web.services.api.representations;

import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;

/**
 * Interface for transient representation related services.
 *
 * @author arichard
 */
public interface ITransientRepresentationMetadataSearchService {
    Optional<RepresentationMetadata> findTransientRepresentationById(String representationId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     */
    class NoOp implements ITransientRepresentationMetadataSearchService {

        @Override
        public Optional<RepresentationMetadata> findTransientRepresentationById(String representationId) {
            return Optional.empty();
        }
    }
}
