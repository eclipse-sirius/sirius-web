/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.web.e2e.tests.templates;

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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Used to create a papaya test project.
 *
 * @author frouene
 */
@Profile("test")
@Service
public class TablePapayaProjectTemplateInitializer implements IProjectTemplateInitializer {

    @Override
    public boolean canHandle(String projectTemplateId) {
        return TablePapayaTemplatesProvider.TABLE_PAPAYA_TEMPLATE_ID.equals(projectTemplateId);
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

            var firstPackage = PapayaFactory.eINSTANCE.createPackage();
            firstPackage.setName("Package");
            component.getPackages().add(firstPackage);

            var firstClass = PapayaFactory.eINSTANCE.createClass();
            firstClass.setName("Class 1");
            firstClass.setDescription("This is a class");
            firstPackage.getTypes().add(firstClass);

            var secondClass = PapayaFactory.eINSTANCE.createClass();
            secondClass.setName("Class 2");
            firstPackage.getTypes().add(secondClass);

            var thirdClass = PapayaFactory.eINSTANCE.createClass();
            thirdClass.setName("Class 3");
            firstPackage.getTypes().add(thirdClass);

            var fourthClass = PapayaFactory.eINSTANCE.createClass();
            fourthClass.setName("Class 4");
            firstPackage.getTypes().add(fourthClass);

            var firstRecord = PapayaFactory.eINSTANCE.createRecord();
            firstRecord.setName("Record 1");
            firstRecord.setDescription("This is a record");
            firstPackage.getTypes().add(firstRecord);

            var secondRecord = PapayaFactory.eINSTANCE.createRecord();
            secondRecord.setName("Record 2");
            firstPackage.getTypes().add(secondRecord);

            var thirdRecord = PapayaFactory.eINSTANCE.createRecord();
            thirdRecord.setName("Record 3");
            firstPackage.getTypes().add(thirdRecord);

            resource.getContents().add(project);
        }

        return Optional.empty();
    }
}
