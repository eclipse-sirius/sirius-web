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
package org.eclipse.sirius.components.core.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;

/**
 * Used to obtain the representation metadata from a representation id.
 * The representation may not be loaded/active.
 *
 * @author pcdavid
 */
public interface IRepresentationMetadataProvider {
    Optional<RepresentationMetadata> getMetadata(String representationId);

    /**
     * Empty implementation, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements IRepresentationMetadataProvider {

        @Override
        public Optional<RepresentationMetadata> getMetadata(String representationId) {
            return Optional.empty();
        }
    }
}
