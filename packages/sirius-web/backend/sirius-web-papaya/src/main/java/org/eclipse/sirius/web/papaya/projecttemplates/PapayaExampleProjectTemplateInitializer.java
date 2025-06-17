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

package org.eclipse.sirius.web.papaya.projecttemplates;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.eclipse.sirius.web.papaya.factories.services.EObjectIndexer;
import org.springframework.stereotype.Service;

/**
 * Used to initialize the Sirius Web papaya example project.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaExampleProjectTemplateInitializer implements IProjectTemplateInitializer {

    private final ObjectMapper objectMapper;

    public PapayaExampleProjectTemplateInitializer(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public boolean canHandle(String projectTemplateId) {
        return PapayaProjectTemplateProvider.SIRIUS_WEB_PROJECT_TEMPLATE_ID.equals(projectTemplateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(ICause cause, String projectTemplateId, IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var documentId = UUID.randomUUID();
            var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
            var resourceMetadataAdapter = new ResourceMetadataAdapter("Sirius");
            resource.eAdapters().add(resourceMetadataAdapter);

            emfEditingContext.getDomain().getResourceSet().getResources().add(resource);

            Project project = PapayaFactory.eINSTANCE.createProject();
            project.setName("Sirius Web");
            resource.getContents().add(project);

            PlanningObjectFactory planningObjectFactory = new PlanningObjectFactory(this.objectMapper, project);
            planningObjectFactory.create(emfEditingContext);

            OperationalAnalysisObjectFactory operationalAnalysisObjectFactory = new OperationalAnalysisObjectFactory(project);
            operationalAnalysisObjectFactory.create(emfEditingContext);

            var eObjectIndexer = new EObjectIndexer();
            eObjectIndexer.index(emfEditingContext.getDomain().getResourceSet());

            operationalAnalysisObjectFactory.link(eObjectIndexer);

        }
        return Optional.empty();
    }
}
