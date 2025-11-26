/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.project.services.api.ISemanticDataInitializer;
import org.springframework.stereotype.Service;

/**
 * Provides Flow-specific project templates initializers.
 *
 * @author pcdavid
 */
@Service
public class FlowSemanticDataInitializer implements ISemanticDataInitializer {

    private final IEditingContextPersistenceService editingContextPersistenceService;

    public FlowSemanticDataInitializer(IEditingContextPersistenceService editingContextPersistenceService) {
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
    }

    @Override
    public boolean canHandle(String templateId) {
        return FlowProjectTemplatesProvider.FLOW_TEMPLATE_ID.equals(templateId);
    }

    @Override
    public void handle(ICause cause, IEditingContext editingContext, String templateId) {
        if (FlowProjectTemplatesProvider.FLOW_TEMPLATE_ID.equals(templateId) && editingContext instanceof IEMFEditingContext emfEditingContext) {
            var documentId = UUID.randomUUID();
            var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
            var resourceMetadataAdapter = new ResourceMetadataAdapter("Flow");
            resource.eAdapters().add(resourceMetadataAdapter);
            emfEditingContext.getDomain().getResourceSet().getResources().add(resource);

            resource.getContents().add(this.getRobotFlowContent());

            this.editingContextPersistenceService.persist(new FlowTemplateInitialization(UUID.randomUUID(), emfEditingContext, templateId, cause), editingContext);
        }
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
}
