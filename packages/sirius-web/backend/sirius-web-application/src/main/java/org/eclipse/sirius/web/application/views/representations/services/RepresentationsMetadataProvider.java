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
package org.eclipse.sirius.web.application.views.representations.services;

import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.forms.Form;

/**
 * Provides the metadata for the "Representations" representation.
 *
 * @author pcdavid
 */
public class RepresentationsMetadataProvider implements IRepresentationMetadataProvider {

    @Override
    public Optional<RepresentationMetadata> getMetadata(String representationId) {
        if (representationId.startsWith(RepresentationsFormDescriptionProvider.PREFIX)) {
            var representationMetadata = RepresentationMetadata.newRepresentationMetadata(representationId)
                    .kind(Form.KIND)
                    .label(RepresentationsFormDescriptionProvider.TITLE)
                    .descriptionId(RepresentationsFormDescriptionProvider.FORM_DESCRIPTION_ID)
                    .build();

            return Optional.of(representationMetadata);
        }
        return Optional.empty();
    }

}
