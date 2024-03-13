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
package org.eclipse.sirius.components.flow.starter.configuration;

import fr.obeo.dsl.designer.sample.flow.CompositeProcessor;
import fr.obeo.dsl.designer.sample.flow.DataFlow;
import fr.obeo.dsl.designer.sample.flow.DataSource;
import fr.obeo.dsl.designer.sample.flow.FlowElementUsage;
import fr.obeo.dsl.designer.sample.flow.FlowFactory;
import fr.obeo.dsl.designer.sample.flow.Processor;
import fr.obeo.dsl.designer.sample.flow.System;

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
import org.eclipse.sirius.components.flow.starter.helper.StereotypeBuilder;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Provides Flow-specific project templates initializers.
 *
 * @author pcdavid
 */
@Configuration
public class FlowProjectTemplatesInitializer implements IProjectTemplateInitializer {

    private static final String DOCUMENT_TITLE = "FlowNewModel";

    private final Logger logger = LoggerFactory.getLogger(FlowProjectTemplatesInitializer.class);

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final StereotypeBuilder stereotypeBuilder;

    public FlowProjectTemplatesInitializer(IProjectRepository projectRepository, IDocumentRepository documentRepository, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IDiagramCreationService diagramCreationService, IRepresentationPersistenceService representationPersistenceService, MeterRegistry meterRegistry) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.stereotypeBuilder = new StereotypeBuilder("flow-template-initializer", meterRegistry);
    }

    @Override
    public boolean canHandle(String templateId) {
        return FlowProjectTemplatesProvider.FLOW_TEMPLATE_ID.equals(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        if (FlowProjectTemplatesProvider.FLOW_TEMPLATE_ID.equals(templateId)) {
            return this.initializeFlowProject(editingContext);
        }
        return Optional.empty();
    }

    private Optional<RepresentationMetadata> initializeFlowProject(IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);
        Optional<UUID> editingContextUUID = new IDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && editingContextUUID.isPresent()) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

            var optionalDocumentEntity = this.projectRepository.findById(editingContextUUID.get()).map(projectEntity -> {
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setProject(projectEntity);
                documentEntity.setName(DOCUMENT_TITLE);
                documentEntity.setContent(this.getRobotFlowContent());

                documentEntity = this.documentRepository.save(documentEntity);
                return documentEntity;
            });

            if (optionalDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalDocumentEntity.get();

                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);

                    var optionalTopographyDiagram = this.findDiagramDescription(editingContext, "Topography");
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

                resource.eAdapters().add(new ResourceMetadataAdapter(DOCUMENT_TITLE));

                resourceSet.getResources().add(resource);
            }
        }
        return result;
    }

    private String getRobotFlowContent() {
        System system = FlowFactory.eINSTANCE.createSystem();
        system.setName("NewSystem");

        CompositeProcessor compositeProcessor = FlowFactory.eINSTANCE.createCompositeProcessor();
        compositeProcessor.setName("CompositeProcessor1");
        compositeProcessor.setCapacity(10);
        compositeProcessor.setLoad(0);
        system.getElements().add(compositeProcessor);

        Processor processor = FlowFactory.eINSTANCE.createProcessor();
        processor.setName("Processor1");
        processor.setCapacity(10);
        processor.setLoad(0);
        processor.setVolume(2);
        processor.setWeight(10);
        compositeProcessor.getElements().add(processor);

        DataSource dataSource = FlowFactory.eINSTANCE.createDataSource();
        dataSource.setName("DataSource1");
        dataSource.setVolume(6);
        system.getElements().add(dataSource);

        DataFlow dataFlow = FlowFactory.eINSTANCE.createDataFlow();
        dataFlow.setUsage(FlowElementUsage.STANDARD);
        dataFlow.setCapacity(6);
        dataFlow.setLoad(6);
        dataFlow.setTarget(processor);
        dataSource.getOutgoingFlows().add(dataFlow);

        return this.stereotypeBuilder.getStereotypeBody(List.of(system));
    }

    private Optional<DiagramDescription> findDiagramDescription(IEditingContext editingContext, String label) {
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .filter(diagramDescription -> Objects.equals(label, diagramDescription.getLabel()))
                .findFirst();
    }

}
