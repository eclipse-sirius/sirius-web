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
package org.eclipse.sirius.web.services.representations;

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
        if (representationId.startsWith(RepresentationsDescriptionProvider.PREFIX)) {
            return Optional.of(new RepresentationMetadata(representationId, Form.KIND, RepresentationsDescriptionProvider.TITLE, RepresentationsDescriptionProvider.REPRESENTATIONS_DEFAULT_FORM_DESCRIPTION_ID));
        }
        return Optional.empty();
    }

}
