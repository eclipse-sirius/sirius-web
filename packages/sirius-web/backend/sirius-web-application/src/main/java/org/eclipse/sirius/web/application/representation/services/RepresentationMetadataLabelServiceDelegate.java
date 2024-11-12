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
package org.eclipse.sirius.web.application.representation.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.DefaultLabelService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationIconURL;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * The label service delegate used compute the label from a representation metadata.
 *
 * @author gcoutable
 */
@Service
public class RepresentationMetadataLabelServiceDelegate implements ILabelServiceDelegate {

    private final List<IRepresentationImageProvider> representationImageProviders;

    public RepresentationMetadataLabelServiceDelegate(List<IRepresentationImageProvider> representationImageProviders) {
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
    }

    @Override
    public boolean canHandle(Object object) {
        return Optional.ofNullable(object)
                .filter(RepresentationMetadata.class::isInstance)
                .isPresent();
    }

    @Override
    public String getLabel(Object object) {
        return this.getStyledLabel(object).toString();
    }

    @Override
    public StyledString getStyledLabel(Object object) {
        String label = Optional.ofNullable(object)
                .filter(RepresentationMetadata.class::isInstance)
                .map(RepresentationMetadata.class::cast)
                .map(RepresentationMetadata::getLabel)
                .orElse("");
        return StyledString.of(label);
    }

    @Override
    public String getFullLabel(Object object) {
        return this.getLabel(object);
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        return Optional.empty();
    }

    @Override
    public boolean isLabelEditable(Object object) {
        return false;
    }

    @Override
    public List<String> getImagePath(Object object) {
        List<String> result = List.of(DefaultLabelService.DEFAULT_ICON_PATH);
        if (object instanceof RepresentationMetadata representationMetadata) {
            if (!representationMetadata.getIconURLs().isEmpty()) {
                result = representationMetadata.getIconURLs().stream()
                        .map(RepresentationIconURL::url)
                        .toList();
            } else {
                result = this.representationImageProviders.stream()
                        .flatMap(provider -> provider.getImageURL(representationMetadata.getKind()).stream())
                        .toList();
            }
        }

        return result;
    }
}
