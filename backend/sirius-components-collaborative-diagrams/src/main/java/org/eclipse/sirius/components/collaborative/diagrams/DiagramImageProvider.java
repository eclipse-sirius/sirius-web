/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Provide the image representing a diagram.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramImageProvider implements IRepresentationImageProvider {

    @Override
    public Optional<String> getImageURL(IRepresentation representation) {
        // @formatter:off
        return Optional.of(representation)
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast)
                .map(diagram -> "/diagram-images/diagram.svg"); //$NON-NLS-1$
        // @formatter:on
    }

}
