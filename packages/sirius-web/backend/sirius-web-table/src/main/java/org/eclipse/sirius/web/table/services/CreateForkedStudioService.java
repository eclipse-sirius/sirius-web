/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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

package org.eclipse.sirius.web.table.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.table.TablePackage;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.project.dto.NatureDTO;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.table.dto.CreateProjectSuccessPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation ICreateForkedStudioService used to create a forked studio.
 *
 * @author mcharfadi
 */
@Service
public class CreateForkedStudioService implements ICreateForkedStudioService {

    private final Logger logger = LoggerFactory.getLogger(CreateForkedStudioService.class);

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IObjectService objectService;

    private final IURLParser urlParser;

    private final IForkedStudioJdbcServices jdbcClient;

    public CreateForkedStudioService(IRepresentationMetadataSearchService representationMetadataSearchService, IProjectSemanticDataSearchService projectSemanticDataSearchService, IObjectService objectService, IURLParser urlParser,
                                     ForkedStudioJdbcClient jdbcClient, ISemanticDataSearchService semanticDataSearchService, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.jdbcClient = Objects.requireNonNull(jdbcClient);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
    }


    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getRepresentationId(String descriptionId) {
        var id = descriptionId.split("\\?cursor");
        return Optional.ofNullable(id[0]);
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getViewDescriptionAsString(RepresentationDescription representationDescription, String sourceElementId) {
        var viewDescription = representationDescription.eContainer();

        if (viewDescription instanceof View view) {
            // Keep only the relevant description
            var copier = new EcoreUtil.Copier();
            View copiedView = (View) copier.copy(view);
            copiedView.getDescriptions().clear();
            copiedView.getDescriptions().add(representationDescription);

            HashMap<Object, Object> options = new HashMap<>();
            options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
            options.put(JsonResource.OPTION_SCHEMA_LOCATION, true);

            ResourceSet resourceSet = new ResourceSetImpl();
            resourceSet.getPackageRegistry().put(ViewPackage.eNS_URI, ViewPackage.eINSTANCE);
            resourceSet.getPackageRegistry().put(TablePackage.eNS_URI, TablePackage.eINSTANCE);

            JSONResourceFactory jsonResourceFactory = new JSONResourceFactory();
            JsonResource jsonResource = jsonResourceFactory.createResource(URI.createURI("sirius://inmemory.json"));
            jsonResource.getContents().add(copiedView);

            Optional<byte[]> optionalBytes;

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
                jsonResource.save(outputStream, options);
                optionalBytes = Optional.of(outputStream.toByteArray());
                return Optional.of(new String(optionalBytes.get(), StandardCharsets.UTF_8));
            } catch (IOException exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        }

        return Optional.empty();
    }

    @Transactional
    private Optional<Project> createProject(IRepresentationInput representationInput, IEditingContext siriusEditingContext, RepresentationMetadata representationMetadata, String representationDescriptionId, String sourceElementId, String sourceId) {
        var representationDescription = this.viewRepresentationDescriptionSearchService.findViewsBySourceId(siriusEditingContext, sourceId).stream()
                .flatMap(view -> view.getDescriptions().stream())
                .filter(description -> objectService.getId(description).equals(sourceElementId))
                .findFirst();

        if (representationDescription.isPresent()) {
            var currentName = representationMetadata.getLabel();
            var newName = "Forked " + currentName;
            var project = this.jdbcClient.createStudioProject(representationInput, newName);
            var viewToSerialize = getViewDescriptionAsString(representationDescription.get(), sourceElementId);

            if (viewToSerialize.isPresent() && project.isPresent() && project.get().getId() != null) {
                var semanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(project.get().getId()))
                        .map(ProjectSemanticData::getSemanticData)
                        .map(AggregateReference::getId)
                        .flatMap(this.semanticDataSearchService::findById);

                if (semanticData.isPresent() && semanticData.get().getId() != null) {
                    String semanticProjectId = semanticData.get().getId().toString();

                    // Update ids of the view
                    var newSourceElementID = UUID.randomUUID();
                    var view = representationDescription.get().eContainer();
                    var viewId = objectService.getId(view);
                    var newViewContent = viewToSerialize.get()
                            .replace(sourceElementId, newSourceElementID.toString())
                            .replace(viewId, UUID.randomUUID().toString());

                    // Insert the forked view into the newly created document
                    var documentId = UUID.randomUUID();
                    this.jdbcClient.insertViewDocument(documentId, UUID.fromString(semanticProjectId), newViewContent, "forked table representation");

                    // Update semanticDataDomain of the forked document
                    this.jdbcClient.updateSemanticDataDomainProjectId(UUID.fromString(semanticProjectId));

                    // Update the descriptionId of the representationMetadata & representation
                    var newDescriptionId = representationDescriptionId
                            .replace(sourceId, documentId.toString())
                            .replace(sourceElementId, newSourceElementID.toString());
                    this.jdbcClient.updateRepresentationMetataDataDescriptionId(representationMetadata.getId(), newDescriptionId);
                    this.jdbcClient.updateRepresentationContentDescriptionId(representationInput, representationDescriptionId, newDescriptionId, sourceId, documentId.toString());

                    return project;
                }
            }
        }
        return Optional.empty();
    }

    public IPayload create(IInput input, IEditingContext editingContext) {
        IPayload payload = new ErrorPayload(input.id(), "call message service");
        if (input instanceof IRepresentationInput representationInput && getRepresentationId(representationInput.representationId()).isPresent()) {
            var representationId = getRepresentationId(representationInput.representationId()).get();
            var representationMetadata = representationMetadataSearchService.findMetadataById(UUID.fromString(representationId));
            if (representationMetadata.isPresent()) {
                var representationDescriptionId = representationMetadata.get().getDescriptionId();

                var sourceElementId = getSourceElementId(representationDescriptionId);
                var sourceId = getSourceId(representationDescriptionId);

                if (sourceElementId.isPresent() && sourceId.isPresent()) {
                    var project = createProject(representationInput, editingContext, representationMetadata.get(), representationDescriptionId, sourceElementId.get(), sourceId.get());
                    if (project.isPresent()) {
                        payload = new CreateProjectSuccessPayload(input.id(), this.projectToDTO(project.get()));
                    }
                }
            }
        }
        return payload;
    }

    private ProjectDTO projectToDTO(Project project) {
        var natures = project.getNatures().stream()
                .map(nature -> new NatureDTO(nature.name()))
                .toList();
        return new ProjectDTO(project.getId(), project.getName(), natures);
    }

}
