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
package org.eclipse.sirius.web.view.fork.services;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.eclipse.sirius.web.view.fork.dto.ForkSemanticDataUpdatedEvent;
import org.eclipse.sirius.web.view.fork.services.api.IForkedViewSemanticDataInitializer;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used initialize the semantic data of the forked project.
 *
 * @author sbegaudeau
 */
@Service
public class ForkedViewSemanticDataInitializer implements IForkedViewSemanticDataInitializer {

    private final IIdentityService identityService;

    private final IResourceToDocumentService resourceToDocumentService;

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    public ForkedViewSemanticDataInitializer(IIdentityService identityService, IResourceToDocumentService resourceToDocumentService, ISemanticDataUpdateService semanticDataUpdateService, IProjectSemanticDataSearchService projectSemanticDataSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
    }

    @Override
    public void initialize(ICause cause, AggregateReference<Project, String> project, RepresentationDescription representationDescription, String representationDescriptionId, String sourceId, String sourceElementId, String newSourceElementId) {
        View viewDescription = (View) representationDescription.eContainer();
        viewDescription.getDescriptions().removeAll(viewDescription.getDescriptions().stream().filter(description -> description != representationDescription).toList());
        var optionalDocumentData = this.resourceToDocumentService.toDocument(viewDescription.eResource(), false);
        var viewId = identityService.getId(viewDescription);

        if (optionalDocumentData.isPresent()) {
            var documentData = optionalDocumentData.get();
            var domainUris = new LinkedHashSet<>(optionalDocumentData.get().ePackageEntries().stream().map(EPackageEntry::nsURI).toList());

            var newContent = documentData.document().getContent()
                    .replace(sourceElementId, newSourceElementId)
                    .replace(viewId, UUID.randomUUID().toString());

            var documentId = UUID.randomUUID();
            var documentToInsert = Document.newDocument(documentId)
                    .name(documentData.document().getName())
                    .content(newContent)
                    .build();

            var semanticDataUpdatedEvent = new ForkSemanticDataUpdatedEvent(cause.id(), cause, sourceId, sourceElementId, representationDescriptionId, documentId.toString(), newSourceElementId);
            this.projectSemanticDataSearchService.findByProjectId(project)
                    .map(ProjectSemanticData::getSemanticData)
                    .ifPresent(semanticData -> this.semanticDataUpdateService.updateDocuments(semanticDataUpdatedEvent, semanticData, Set.of(documentToInsert), domainUris));
        }
    }
}
