/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.sample.configuration.StereotypeBuilder;
import org.eclipse.sirius.web.sample.papaya.domain.PapayaDomainProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewProvider;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Provides a initializer for "Papaya Studio" (domain & view).
 *
 * @author pcdavid
 */
@Configuration
public class PapayaStudioTemplatesInitializer implements IProjectTemplateInitializer {

    private static final String DOMAIN_DOCUMENT_NAME = "Papaya Domain";

    private static final String VIEW_DOCUMENT_NAME = "Papaya View";

    private final Logger logger = LoggerFactory.getLogger(PapayaStudioTemplatesInitializer.class);

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final StereotypeBuilder stereotypeBuilder;

    public PapayaStudioTemplatesInitializer(IProjectRepository projectRepository, IDocumentRepository documentRepository, IRepresentationDescriptionSearchService representationDescriptionSearchService,
                                            IDiagramCreationService diagramCreationService, IRepresentationPersistenceService representationPersistenceService, MeterRegistry meterRegistry) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.stereotypeBuilder = new StereotypeBuilder(PapayaStudioTemplateProvider.STUDIO_TEMPLATE_ID, meterRegistry);
    }

    @Override
    public boolean canHandle(String templateId) {
        return PapayaStudioTemplateProvider.STUDIO_TEMPLATE_ID.equals(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        if (PapayaStudioTemplateProvider.STUDIO_TEMPLATE_ID.equals(templateId)) {
            return this.initializeStudioProject(editingContext);
        }
        return Optional.empty();
    }

    private Optional<RepresentationMetadata> initializeStudioProject(IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        // @formatter:off
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);
        // @formatter:on
        Optional<UUID> editingContextUUID = new IDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && editingContextUUID.isPresent()) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

            var optionalDomainDocumentEntity = this.projectRepository.findById(editingContextUUID.get()).map(projectEntity -> {
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setProject(projectEntity);
                documentEntity.setName(DOMAIN_DOCUMENT_NAME);
                documentEntity.setContent(this.stereotypeBuilder.getStereotypeBody(new PapayaDomainProvider().getDomains()));

                documentEntity = this.documentRepository.save(documentEntity);
                return documentEntity;
            });

            if (optionalDomainDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalDomainDocumentEntity.get();

                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);

                    var optionalTopographyDiagram = this.findDiagramDescription(editingContext, "Domain");
                    if (optionalTopographyDiagram.isPresent()) {
                        DiagramDescription topographyDiagram = optionalTopographyDiagram.get();
                        Object semanticTarget = resource.getContents().get(0);

                        Diagram diagram = this.diagramCreationService.create(topographyDiagram.getLabel(), semanticTarget, topographyDiagram, editingContext);
                        this.representationPersistenceService.save(null, editingContext, diagram);

                        result = Optional.of(new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId()));
                    }
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }

                resource.eAdapters().add(new ResourceMetadataAdapter(DOMAIN_DOCUMENT_NAME));

                resourceSet.getResources().add(resource);
            }

            var optionalViewDocumentEntity = this.projectRepository.findById(editingContextUUID.get()).map(projectEntity -> {
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setProject(projectEntity);
                documentEntity.setName(VIEW_DOCUMENT_NAME);
                documentEntity.setContent(this.stereotypeBuilder.getStereotypeBody(List.of(new PapayaViewProvider().getView())));

                documentEntity = this.documentRepository.save(documentEntity);
                return documentEntity;
            });
            if (optionalViewDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalViewDocumentEntity.get();
                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                resource.eAdapters().add(new ResourceMetadataAdapter(VIEW_DOCUMENT_NAME));
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }
                resourceSet.getResources().add(resource);
            }
        }
        return result;
    }

    private Optional<DiagramDescription> findDiagramDescription(IEditingContext editingContext, String label) {
        // @formatter:off
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .filter(diagramDescrpition -> Objects.equals(label, diagramDescrpition.getLabel()))
                .findFirst();
        // @formatter:on
    }

}
