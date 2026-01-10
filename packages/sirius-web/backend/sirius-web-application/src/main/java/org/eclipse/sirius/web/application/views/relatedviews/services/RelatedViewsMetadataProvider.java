/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.relatedviews.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

/**
 * Provides the metadata for the "Related views" representation.
 *
 * @author pcdavid
 */
public class RelatedViewsMetadataProvider implements IRepresentationMetadataProvider {

    private final IMessageService messageService;

    public RelatedViewsMetadataProvider(IMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public Optional<RepresentationMetadata> getMetadata(String editingContextId, String representationId) {
        if (representationId.startsWith(RelatedViewsFormDescriptionProvider.PREFIX)) {
            var representationMetadata = RepresentationMetadata.newRepresentationMetadata(representationId)
                    .kind(Form.KIND)
                    .label(this.messageService.relatedViewsLabel())
                    .descriptionId(RelatedViewsFormDescriptionProvider.FORM_DESCRIPTION_ID)
                    .iconURLs(List.of("related-views/related-views.svg"))
                    .build();

            return Optional.of(representationMetadata);
        }
        return Optional.empty();
    }

}
