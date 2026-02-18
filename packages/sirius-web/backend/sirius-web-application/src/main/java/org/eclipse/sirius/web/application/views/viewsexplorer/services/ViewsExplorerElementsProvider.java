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
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.viewsexplorer.domain.RepresentationDescriptionType;
import org.eclipse.sirius.web.application.views.viewsexplorer.domain.RepresentationKind;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IViewsExplorerElementsProvider;
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
public class ViewsExplorerElementsProvider implements IViewsExplorerElementsProvider {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IURLParser urlParser;

    public ViewsExplorerElementsProvider(IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IURLParser urlParser) {
        this.representationMetadataSearchService = representationMetadataSearchService;
        this.representationDescriptionSearchService = representationDescriptionSearchService;
        this.urlParser = urlParser;
    }

    @Override
    public List<RepresentationKind> getElements(IEditingContext editingContext) {
        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        if (optionalSemanticDataId.isPresent()) {
            List<RepresentationMetadata> allRepresentationMetadata = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(
                    AggregateReference.to(optionalSemanticDataId.get()));
            Map<String, IRepresentationDescription> allRepresentationDescription = this.representationDescriptionSearchService.findAll(editingContext);

            List<RepresentationDescriptionType> descType = allRepresentationMetadata.stream().collect(Collectors.groupingBy(RepresentationMetadata::getDescriptionId)).entrySet().stream()
                    .map(entry -> Optional.ofNullable(allRepresentationDescription.get(entry.getKey())).map(desc -> new RepresentationDescriptionType(entry.getKey(), desc, entry.getValue())))
                    .flatMap(Optional::stream).toList();

            return descType.stream().collect(Collectors.groupingBy(reprDesc -> reprDesc.representationsMetadata().get(0).getKind())).entrySet().stream()
                    .map(entry -> new RepresentationKind(entry.getKey(), this.urlParser.getParameterValues(entry.getKey()).get("type").get(0),
                            entry.getValue())).toList();
        }
        return List.of();
    }
}
