/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.object.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.api.IDefaultLabelService;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFLabelService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationIconURL;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * Provides the default implementation of the label service.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultLabelService implements IDefaultLabelService {

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final IEMFLabelService emfLabelService;

    public DefaultLabelService(List<IRepresentationImageProvider> representationImageProviders, IEMFLabelService emfLabelService) {
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.emfLabelService = Objects.requireNonNull(emfLabelService);
    }

    @Override
    public StyledString getStyledLabel(Object self) {
        StyledString label = StyledString.of("");
        if (self instanceof RepresentationMetadata representationMetadata) {
            label = StyledString.of(representationMetadata.getLabel());
        } else if (self instanceof Resource resource) {
            label = resource.eAdapters().stream()
                    .filter(ResourceMetadataAdapter.class::isInstance)
                    .map(ResourceMetadataAdapter.class::cast).findFirst()
                    .map(ResourceMetadataAdapter::getName)
                    .map(StyledString::of)
                    .orElse(StyledString.of(resource.getURI().lastSegment()));
        } else if (self instanceof EObject eObject) {
            label = this.emfLabelService.getStyledLabel(eObject);
        }
        return label;
    }

    @Override
    public List<String> getImagePaths(Object self) {
        List<String> imagePaths = List.of(CoreImageConstants.DEFAULT_SVG);
        if (self instanceof RepresentationMetadata representationMetadata) {
            if (!representationMetadata.getIconURLs().isEmpty()) {
                imagePaths = representationMetadata.getIconURLs().stream()
                        .map(RepresentationIconURL::url)
                        .toList();
            } else {
                imagePaths = this.representationImageProviders.stream()
                        .flatMap(provider -> provider.getImageURL(representationMetadata.getKind()).stream())
                        .toList();
            }
        } else if (self instanceof Resource) {
            imagePaths = List.of("/icons/Resource.svg");
        } else if (self instanceof EObject eObject) {
            imagePaths = this.emfLabelService.getImagePaths(eObject);
        }
        return imagePaths;
    }
}
