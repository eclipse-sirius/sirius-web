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
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IDefaultViewsExplorerContentService;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IViewsExplorerContentService;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IViewsExplorerContentServiceDelegate;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Service in charge of computing the list of representations available in the "views explorer" view, directly organized into a synthetic semantic tree.
 *
 * @author tgiraudet
 */
@Service
public class ComposedViewsExplorerContentService implements IViewsExplorerContentService {

    private final IDefaultViewsExplorerContentService defaultViewsExplorerContentService;

    private final List<IViewsExplorerContentServiceDelegate> viewsExplorerContentServiceDelegates;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public ComposedViewsExplorerContentService(List<IViewsExplorerContentServiceDelegate> viewsExplorerContentServiceDelegates,
        IDefaultViewsExplorerContentService defaultViewsExplorerContentService, IRepresentationMetadataSearchService representationMetadataSearchService,
        IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.viewsExplorerContentServiceDelegates = Objects.requireNonNull(viewsExplorerContentServiceDelegates);
        this.defaultViewsExplorerContentService = Objects.requireNonNull(defaultViewsExplorerContentService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    public List<RepresentationKind> getContents(IEditingContext editingContext) {
        return new UUIDParser().parse(editingContext.getId())
                .map(semanticDataId -> {
                    var allMetadata = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(semanticDataId));
                    var allDescriptions = this.representationDescriptionSearchService.findAll(editingContext);
                    return this.viewsExplorerContentServiceDelegates.stream()
                            .filter(delegate -> delegate.canHandle(editingContext))
                            .findFirst()
                            .map(delegate -> delegate.getContents(editingContext, allMetadata, allDescriptions))
                            .orElseGet(() -> this.defaultViewsExplorerContentService.getContents(editingContext, allMetadata, allDescriptions));
                })
                .orElse(List.of());
    }
}
