/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.springframework.stereotype.Service;

/**
 * Used to create the empty Papaya project.
 *
 * @author sbegaudeau
 */
@Service
public class EmptyPapayaProjectTemplateInitializer implements IProjectTemplateInitializer {
    @Override
    public boolean canHandle(String projectTemplateId) {
        return PapayaProjectTemplateProvider.EMPTY_PROJECT_TEMPLATE_ID.equals(projectTemplateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(ICause cause, String projectTemplateId, IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var documentId = UUID.randomUUID();
            var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
            var resourceMetadataAdapter = new ResourceMetadataAdapter("Papaya");
            resource.eAdapters().add(resourceMetadataAdapter);
            emfEditingContext.getDomain().getResourceSet().getResources().add(resource);

            var project = PapayaFactory.eINSTANCE.createProject();
            project.setName("Project");

            var component = PapayaFactory.eINSTANCE.createComponent();
            component.setName("Component");
            project.getElements().add(component);

            resource.getContents().add(project);
        }

        return Optional.empty();
    }
}
