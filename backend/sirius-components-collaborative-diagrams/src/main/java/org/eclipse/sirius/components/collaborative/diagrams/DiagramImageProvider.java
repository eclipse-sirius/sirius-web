/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import org.springframework.stereotype.Service;

/**
 * Provide the image representing a diagram.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramImageProvider implements IRepresentationImageProvider {

    @Override
    public Optional<String> getImageURL(String kind) {
        if (Diagram.KIND.equals(kind)) {
            return Optional.of("/diagram-images/diagram.svg"); //$NON-NLS-1$
        }
        return Optional.empty();
    }

}
