/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.application.studio.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IWorkbenchConfigurationProviderDelegate;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.DefaultViewConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchMainPanelConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchRepresentationEditorConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchSidePanelConfiguration;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used to configure the workbench for a studio project.
 *
 * @author gcoutable
 */
@Service
public class StudioWorkbenchConfigurationProvider implements IWorkbenchConfigurationProviderDelegate {

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public StudioWorkbenchConfigurationProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate, IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public boolean canHandle(String editingContextId) {
        return this.studioCapableEditingContextPredicate.test(editingContextId);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkbenchConfiguration getWorkbenchConfiguration(String editingContextId) {
        var allRepresentationMetadata = new UUIDParser().parse(editingContextId).map(id -> this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(id))).orElse(List.of());
        var optionalRepresentationIdToOpen = allRepresentationMetadata.stream()
                .filter(representationMetadata -> "Domain".equals(representationMetadata.getLabel()))
                .map(RepresentationMetadata::getRepresentationMetadataId)
                .findFirst();
        var representationToOpen = optionalRepresentationIdToOpen.stream()
                .map(representationMetadata -> new WorkbenchRepresentationEditorConfiguration(representationMetadata.toString(), true))
                .toList();

        return new WorkbenchConfiguration(
                new WorkbenchMainPanelConfiguration("main", representationToOpen),
                List.of(
                        new WorkbenchSidePanelConfiguration("left", true, List.of(
                                new DefaultViewConfiguration("explorer", true),
                                new DefaultViewConfiguration("validation", false),
                                new DefaultViewConfiguration("search", false)
                        )),
                        new WorkbenchSidePanelConfiguration("right", true, List.of(
                                new DefaultViewConfiguration("details", true),
                                new DefaultViewConfiguration("query", false),
                                new DefaultViewConfiguration("representations", false),
                                new DefaultViewConfiguration("related-elements", false)
                        ))
                )
        );
    }
}
