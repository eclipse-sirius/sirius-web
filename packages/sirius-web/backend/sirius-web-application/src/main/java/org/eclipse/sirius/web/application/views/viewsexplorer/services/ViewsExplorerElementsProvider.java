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
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Service in charge of providing the list of representations available in the "views explorer" view, directly organized into a synthetic semantic tree.
 *
 * @author theogiraudet
 */
@Service
public class ViewsExplorerElementsProvider {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IURLParser urlParser;

    public ViewsExplorerElementsProvider(IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IURLParser urlParser) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    public List<RepresentationKind> getElements(IEditingContext editingContext) {
        return new UUIDParser().parse(editingContext.getId())
                .map(semanticDataId -> {
                    var allMetadata = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(semanticDataId));
                    var allDescriptions = this.representationDescriptionSearchService.findAll(editingContext);
                    var descriptionTypes = this.groupByDescriptionType(allMetadata, allDescriptions);
                    return this.groupByKind(descriptionTypes);
                })
                .orElse(List.of());
    }

    private List<RepresentationDescriptionType> groupByDescriptionType(List<RepresentationMetadata> allMetadata, Map<String, IRepresentationDescription> allDescriptions) {
        return allMetadata.stream()
                .collect(Collectors.groupingBy(RepresentationMetadata::getDescriptionId))
                .entrySet().stream()
                .map(entry -> Optional.ofNullable(allDescriptions.get(entry.getKey()))
                        .map(desc -> new RepresentationDescriptionType(entry.getKey(), desc, entry.getValue())))
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
