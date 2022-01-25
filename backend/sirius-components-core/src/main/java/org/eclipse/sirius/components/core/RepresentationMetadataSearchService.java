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
package org.eclipse.sirius.components.core;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Used to find the metadata of a representation.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationMetadataSearchService implements IRepresentationMetadataSearchService {

    @Override
    public Optional<RepresentationMetadata> findByRepresentation(IRepresentation representation) {
        return Optional.of(new RepresentationMetadata(representation.getId(), representation.getKind(), representation.getLabel(), representation.getDescriptionId()));
    }

}
