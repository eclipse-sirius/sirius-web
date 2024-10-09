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
package org.eclipse.sirius.components.flow.starter.services;

import fr.obeo.dsl.designer.sample.flow.CompositeProcessor;
import fr.obeo.dsl.designer.sample.flow.DataFlow;
import fr.obeo.dsl.designer.sample.flow.DataSource;
import fr.obeo.dsl.designer.sample.flow.FlowElementUsage;
import fr.obeo.dsl.designer.sample.flow.FlowFactory;
import fr.obeo.dsl.designer.sample.flow.Processor;
import fr.obeo.dsl.designer.sample.flow.System;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
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
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Provides Flow-specific project templates initializers.
 *
 * @author pcdavid
 */
@Service
public class FlowProjectTemplatesInitializer implements IProjectTemplateInitializer {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final IRepresentationPersistenceService representationPersistenceService;

    public FlowProjectTemplatesInitializer(IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IDiagramCreationService diagramCreationService, IRepresentationPersistenceService representationPersistenceService, MeterRegistry meterRegistry,
            IRepresentationMetadataPersistenceService representationMetadataPersistenceService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
    }

    @Override
    public boolean canHandle(String templateId) {
        return FlowProjectTemplatesProvider.FLOW_TEMPLATE_ID.equals(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(ICause cause, String templateId, IEditingContext editingContext) {
        if (FlowProjectTemplatesProvider.FLOW_TEMPLATE_ID.equals(templateId)) {
            return this.initializeFlowProject(cause, editingContext);
        }
        return Optional.empty();
    }

    private Optional<RepresentationMetadata> initializeFlowProject(ICause cause, IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var documentId = UUID.randomUUID();
            var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
            var resourceMetadataAdapter = new ResourceMetadataAdapter("Flow");
            resource.eAdapters().add(resourceMetadataAdapter);
            emfEditingContext.getDomain().getResourceSet().getResources().add(resource);

            resource.getContents().add(this.getRobotFlowContent());

            var optionalTopographyDiagram = this.findDiagramDescription(editingContext, "Topography");
            if (optionalTopographyDiagram.isPresent()) {
                DiagramDescription topographyDiagram = optionalTopographyDiagram.get();
                Object semanticTarget = resource.getContents().get(0);

                var variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, semanticTarget);
                variableManager.put(DiagramDescription.LABEL, topographyDiagram.getLabel());
                String label = topographyDiagram.getLabelProvider().apply(variableManager);

                Diagram diagram = this.diagramCreationService.create(semanticTarget, topographyDiagram, editingContext);
                var representationMetadata = RepresentationMetadata.newRepresentationMetadata(diagram.getId())
                        .kind(diagram.getKind())
                        .label(label)
                        .descriptionId(diagram.getDescriptionId())
                        .build();

                this.representationMetadataPersistenceService.save(cause, editingContext, representationMetadata, diagram.getTargetObjectId());
                this.representationPersistenceService.save(cause, editingContext, diagram);

                result = Optional.of(representationMetadata);
            }
        }
        return result;
    }

    private System getRobotFlowContent() {
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

        return system;
    }

    private Optional<DiagramDescription> findDiagramDescription(IEditingContext editingContext, String label) {
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .filter(diagramDescription -> Objects.equals(label, diagramDescription.getLabel()))
                .findFirst();
    }

}
