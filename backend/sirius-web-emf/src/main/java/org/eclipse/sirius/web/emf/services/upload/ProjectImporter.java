/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services.upload;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessor;
import org.eclipse.sirius.web.persistence.entities.IdMappingEntity;
import org.eclipse.sirius.web.persistence.repositories.IIdMappingRepository;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.UploadDocumentInput;
import org.eclipse.sirius.web.services.api.document.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.ProjectManifest;
import org.eclipse.sirius.web.services.api.projects.RepresentationManifest;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.graphql.api.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to import a project.
 *
 * @author gcoutable
 */
public class ProjectImporter {

    private final Logger logger = LoggerFactory.getLogger(ProjectImporter.class);

    private final UUID projectId;

    private final IProjectEventProcessor projectEventProcessor;

    private final Map<String, UploadFile> documents;

    private final List<RepresentationDescriptor> representations;

    private final ProjectManifest projectManifest;

    private final Context context;

    private final Map<String, Document> oldDocumentIdToNewDocument = new HashMap<>();

    private final IIdMappingRepository idMappingRepository;

    public ProjectImporter(UUID projectId, IProjectEventProcessor projectEventProcessor, Map<String, UploadFile> documents, List<RepresentationDescriptor> representations,
            ProjectManifest projectManifest, Context context, IIdMappingRepository idMappingRepository) {
        this.projectId = Objects.requireNonNull(projectId);
        this.projectEventProcessor = Objects.requireNonNull(projectEventProcessor);
        this.documents = Objects.requireNonNull(documents);
        this.representations = Objects.requireNonNull(representations);
        this.projectManifest = Objects.requireNonNull(projectManifest);
        this.context = Objects.requireNonNull(context);
        this.idMappingRepository = Objects.requireNonNull(idMappingRepository);
    }

    public boolean importProject() {
        boolean errorOccurred = !this.createDocuments();

        if (!errorOccurred) {
            errorOccurred = !this.createRepresentations();
        }

        return !errorOccurred;
    }

    /**
     * Creates all representations in the project thanks to the {@link IProjectEventProcessor} and the create
     * representation input. If at least one representation has not been created it will return <code>false</code>.
     *
     * @return <code>true</code> whether all representations has been created, <code>false</code> otherwise
     */
    private boolean createRepresentations() {
        boolean allRepresentationCreated = true;
        for (RepresentationDescriptor representationDescriptor : this.representations) {
            RepresentationManifest representationManifest = this.projectManifest.getRepresentations().get(representationDescriptor.getId().toString());

            String targetObjectURI = representationManifest.getTargetObjectURI();
            String oldDocumentId = URI.create(targetObjectURI).getPath();
            Document newDocument = this.oldDocumentIdToNewDocument.get(oldDocumentId);
            final String objectId;
            if (newDocument != null) {
                objectId = targetObjectURI.replace(oldDocumentId, newDocument.getId().toString());
            } else {
                objectId = targetObjectURI;
            }
            boolean representationCreated = false;
            Optional<IdMappingEntity> optionalIdMappingEntity = this.idMappingRepository.findByExternalId(representationManifest.getDescriptionURI());
            if (optionalIdMappingEntity.isPresent()) {
                IdMappingEntity idMappingEntity = optionalIdMappingEntity.get();

                CreateRepresentationInput input = new CreateRepresentationInput(this.projectId, idMappingEntity.getId(), objectId, representationDescriptor.getLabel());

                // @formatter:off
                representationCreated = this.projectEventProcessor.handle(input, this.context)
                        .filter(CreateRepresentationSuccessPayload.class::isInstance)
                        .map(CreateRepresentationSuccessPayload.class::cast)
                        .map(CreateRepresentationSuccessPayload::getRepresentation)
                        .isPresent();
                // @formatter:on
            }

            if (!representationCreated) {
                this.logger.error(String.format("The representation %1$s has not been created", representationDescriptor.getLabel())); //$NON-NLS-1$
            }

            allRepresentationCreated = allRepresentationCreated && representationCreated;
        }

        return allRepresentationCreated;
    }

    /**
     * Creates all documents in the project thanks to the {@link IProjectEventProcessor} and the
     * {@link CreateDocumentFromUploadEvent}. If at least one document has not been created it will return
     * <code>false</code>.
     *
     * @return <code>true</code> whether all documents has been created, <code>false</code> otherwise
     */
    private boolean createDocuments() {
        for (Entry<String, UploadFile> entry : this.documents.entrySet()) {
            String oldDocumentId = entry.getKey();
            UploadFile uploadFile = entry.getValue();
            UploadDocumentInput input = new UploadDocumentInput(this.projectId, uploadFile);

            // @formatter:off
            Document document = this.projectEventProcessor.handle(input, this.context)
                    .filter(UploadDocumentSuccessPayload.class::isInstance)
                    .map(UploadDocumentSuccessPayload.class::cast)
                    .map(UploadDocumentSuccessPayload::getDocument)
                    .orElse(null);
            // @formatter:on
            if (document == null) {
                this.logger.error(String.format("The document %1$s has not been created", this.projectManifest.getDocumentIdsToName().get(oldDocumentId))); //$NON-NLS-1$
            }
            this.oldDocumentIdToNewDocument.put(oldDocumentId, document);
        }

        return this.oldDocumentIdToNewDocument.values().stream().allMatch(Objects::nonNull);
    }

}
