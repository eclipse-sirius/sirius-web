/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.web.application.views.viewsexplorer.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IDefaultViewsExplorerContentService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * The default implementation used to compute the content of the views explorer.
 *
 * @author tgiraudet
 */
@Service
public class DefaultViewsExplorerContentService implements IDefaultViewsExplorerContentService {

    private final IURLParser urlParser;

    public DefaultViewsExplorerContentService(IURLParser urlParser) {
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public List<RepresentationKind> getContents(IEditingContext editingContext, List<RepresentationMetadata> representationMetadata, Map<String, IRepresentationDescription> representationDescriptions) {
        var descriptionTypes = this.groupByDescriptionType(representationMetadata, representationDescriptions);
        return this.groupByKind(descriptionTypes);
    }

    private List<RepresentationDescriptionType> groupByDescriptionType(List<RepresentationMetadata> allMetadata, Map<String, IRepresentationDescription> allDescriptions) {
        return allMetadata.stream()
                .collect(Collectors.groupingBy(RepresentationMetadata::getDescriptionId))
                .entrySet().stream()
                .map(entry -> Optional.ofNullable(allDescriptions.get(entry.getKey()))
                        .map(representationDescription -> new RepresentationDescriptionType(entry.getKey(), representationDescription, entry.getValue())))
                .flatMap(Optional::stream)
                .toList();
    }

    private List<RepresentationKind> groupByKind(List<RepresentationDescriptionType> descriptionTypes) {
        return descriptionTypes.stream()
                .collect(Collectors.groupingBy(descType -> descType.representationsMetadata().get(0).getKind()))
                .entrySet().stream()
                .map(entry -> {
                    var kindId = entry.getKey();
                    var kindName = this.urlParser.getParameterValues(kindId).get("type").get(0);
                    return new RepresentationKind(kindId, kindName, entry.getValue());
                })
                .toList();
    }
}
