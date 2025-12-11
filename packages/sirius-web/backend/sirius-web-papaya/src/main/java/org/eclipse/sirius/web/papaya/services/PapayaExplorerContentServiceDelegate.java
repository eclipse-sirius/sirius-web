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
package org.eclipse.sirius.web.papaya.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IDefaultContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerContentServiceDelegate;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.papaya.representations.dashboarddiagram.PapayaDashboardDiagramDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.api.IPapayaCapableEditingContextPredicate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * A specific Papaya Content Service to provide the Dashboard diagram item in Papaya projects.
 *
 * @author fbarbin
 */
@Service
public class PapayaExplorerContentServiceDelegate implements IExplorerContentServiceDelegate {

    public static final String PAPAYA_DASHBOARD_DIAGRAM = "Papaya Dashboard Diagram";

    private final IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate;

    private final IDefaultContentService defaultContentService;

    public PapayaExplorerContentServiceDelegate(IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate, IDefaultContentService defaultContentService) {
        this.papayaCapableEditingContextPredicate = Objects.requireNonNull(papayaCapableEditingContextPredicate);
        this.defaultContentService = Objects.requireNonNull(defaultContentService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext) {
        return this.papayaCapableEditingContextPredicate.test(editingContext.getId());
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext) {
        var content = new ArrayList<>();
        this.getDashboardDiagramMetadata(editingContext).ifPresent(content::add);
        content.addAll(this.defaultContentService.getContents(editingContext));
        return content;
    }

    private Optional<RepresentationMetadata> getDashboardDiagramMetadata(IEditingContext editingContext) {
        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        if (optionalSemanticDataId.isPresent()) {
            var causeID = UUID.randomUUID();
            var dashboardRepresentationMetadata = RepresentationMetadata.newRepresentationMetadata(PapayaDashboardDiagramDescriptionProvider.DASHBOARD_REPRESENTATION_ID)
                    .representationMetadataId(UUID.fromString(PapayaDashboardDiagramDescriptionProvider.DASHBOARD_REPRESENTATION_ID))
                    .semanticData(AggregateReference.to(optionalSemanticDataId.get()))
                    .targetObjectId(editingContext.getId())
                    .descriptionId(PapayaDashboardDiagramDescriptionProvider.DASHBOARD_DESCRIPTION_ID)
                    .label(PAPAYA_DASHBOARD_DIAGRAM)
                    .kind(Diagram.KIND)
                    .documentation("")
                    .iconURLs(List.of()).build(() -> causeID);

            return Optional.of(dashboardRepresentationMetadata);
        }
        return Optional.empty();
    }
}
