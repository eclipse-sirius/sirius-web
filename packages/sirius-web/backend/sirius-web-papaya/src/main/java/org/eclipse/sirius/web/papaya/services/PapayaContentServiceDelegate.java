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
package org.eclipse.sirius.web.papaya.services;

import org.eclipse.sirius.components.core.api.IContentServiceDelegate;
import org.eclipse.sirius.components.core.api.IDefaultContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.papaya.representations.dashboarddiagram.PapayaDashboardDiagramDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.api.IPapayaCapableEditingContextPredicate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * A specific Papaya Content Service to provide the Dashboard diagram item in Papaya projects.
 *
 * @author fbarbin
 */
@Service
public class PapayaContentServiceDelegate implements IContentServiceDelegate {

    public static final String PAPAYA_DASHBOARD_DIAGRAM = "Papaya Dashboard Diagram";

    private final IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate;

    private final IDefaultContentService defaultContentService;

    PapayaContentServiceDelegate(IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate, IDefaultContentService defaultContentService) {
        this.papayaCapableEditingContextPredicate = Objects.requireNonNull(papayaCapableEditingContextPredicate);
        this.defaultContentService = Objects.requireNonNull(defaultContentService);
    }

    private void addDashboardDiagramMetadata(IEditingContext editingContext, ArrayList<Object> content) {
        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        if (optionalSemanticDataId.isPresent()) {
            var causeID = UUID.randomUUID();
            var dashboardRepresetationMetadata = RepresentationMetadata.newRepresentationMetadata(UUID.fromString(PapayaDashboardDiagramDescriptionProvider.DASHBOARD_REPRESENTATION_ID))
                .semanticData(AggregateReference.to(optionalSemanticDataId.get()))
                .targetObjectId(editingContext.getId())
                .descriptionId(PapayaDashboardDiagramDescriptionProvider.DASHBOARD_DESCRIPTION_ID)
                .label(PAPAYA_DASHBOARD_DIAGRAM)
                .kind(Diagram.KIND)
                .documentation("")
                .iconURLs(List.of()).build(() -> causeID);
            content.add(dashboardRepresetationMetadata);
        }
    }

    @Override
    public boolean canHandle(Object object) {
        return Optional.ofNullable(object).filter(IEditingContext.class::isInstance)
            .map(IEditingContext.class::cast)
            .map(IEditingContext::getId)
            .filter(this.papayaCapableEditingContextPredicate)
            .isPresent();
    }

    @Override
    public List<Object> getContents(Object object) {
        var content = new ArrayList<>(defaultContentService.getContents(object));
        Optional.ofNullable(object)
            .filter(IEditingContext.class::isInstance)
            .map(IEditingContext.class::cast)
            .ifPresent(editingContext -> this.addDashboardDiagramMetadata(editingContext, content));
        return content;
    }
}
