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
package org.eclipse.sirius.web.services.relatedelements;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.forms.api.RelatedElementsConfiguration;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.forms.Form;
import org.springframework.stereotype.Service;

/**
 * Provides metadata for the "Related Elements" representation.
 *
 * @author pcdavid
 */
@Service
public class RelatedElementsMetadataProvider implements IRepresentationMetadataProvider {

    @Override
    public Optional<RepresentationMetadata> getMetadata(String representationId) {
        if (representationId.startsWith(RelatedElementsConfiguration.RELATED_PREFIX)) {
            return Optional.of(new RepresentationMetadata(representationId, Form.KIND, DefaultRelatedElementsDescriptionProvider.FORM_TITLE, DefaultRelatedElementsDescriptionProvider.FORM_DESCRIPTION_ID.toString()));
        }
        return Optional.empty();
    }

}
