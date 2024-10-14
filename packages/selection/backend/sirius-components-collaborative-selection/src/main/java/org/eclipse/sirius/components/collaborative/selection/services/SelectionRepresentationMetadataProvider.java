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
package org.eclipse.sirius.components.collaborative.selection.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.springframework.stereotype.Service;

/**
 * A Service to provide metadata for the Selection Dialog Representation.
 *
 * @author fbarbin
 */
@Service
public class SelectionRepresentationMetadataProvider implements IRepresentationMetadataProvider {

    private static final String REPRESENTATION_DESCRIPTION_PARAMETER = "representationDescription";

    private static final String PREFIX = "selectionDialog://";

    private final URLParser urlParser;

    public SelectionRepresentationMetadataProvider(URLParser urlParser) {
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public Optional<RepresentationMetadata> getMetadata(String representationId) {
        if (representationId.startsWith(PREFIX)) {
            Optional<String> optionalRepresentationDescriptionId = Optional.ofNullable(this.urlParser.getParameterValues(representationId).get(REPRESENTATION_DESCRIPTION_PARAMETER))
                    .filter(values -> !values.isEmpty())
                    .map(values -> values.get(0));
            if (optionalRepresentationDescriptionId.isPresent()) {
                var representationMetadata = RepresentationMetadata.newRepresentationMetadata(representationId)
                        .kind("SelectionRepresentation")
                        .label("Selection Representation")
                        .descriptionId(optionalRepresentationDescriptionId.get())
                        .build();

                return Optional.of(representationMetadata);
            }
        }
        return Optional.empty();
    }

}
