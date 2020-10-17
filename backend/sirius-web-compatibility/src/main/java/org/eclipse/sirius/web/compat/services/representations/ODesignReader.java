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
package org.eclipse.sirius.web.compat.services.representations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.PropertiesExtWidgetsReferencePackage;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.viewpoint.description.validation.ValidationPackage;
import org.eclipse.sirius.web.emf.utils.EMFResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Utility class used to load an odesign file.
 *
 * @author smonnier
 */
@Service
public class ODesignReader {

    private static final String ENVIRONMENT_ODESIGN_PATH = "model/Environment.odesign"; //$NON-NLS-1$

    private static final String ENVIRONMENT_ODESIGN_URI = "environment:/viewpoint"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.getLogger(ODesignReader.class);

    public Optional<Group> read(ClassPathResource classPathResource) {
        Optional<Group> optionalGroup = Optional.empty();
        try (InputStream inputStream = classPathResource.getInputStream()) {
            optionalGroup = this.read(classPathResource.getFilename(), inputStream);
        } catch (IOException exception) {
            this.logger.error(exception.getMessage(), exception);
        }
        return optionalGroup;
    }

    private Optional<Group> read(String fileName, InputStream inputStream) {
        ResourceSet resourceSet = new ResourceSetImpl();

        ECrossReferenceAdapter adapter = new ECrossReferenceAdapter();
        resourceSet.eAdapters().add(adapter);

        resourceSet.getPackageRegistry().put(ViewpointPackage.eNS_URI, ViewpointPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(DescriptionPackage.eNS_URI, DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(ValidationPackage.eNS_URI, ValidationPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(org.eclipse.sirius.diagram.description.DescriptionPackage.eNS_URI, org.eclipse.sirius.diagram.description.DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage.eNS_URI,
                org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(org.eclipse.sirius.tree.description.DescriptionPackage.eNS_URI, org.eclipse.sirius.tree.description.DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(org.eclipse.sirius.diagram.sequence.description.DescriptionPackage.eNS_URI, org.eclipse.sirius.diagram.sequence.description.DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(PropertiesPackage.eNS_URI, PropertiesPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(PropertiesExtWidgetsReferencePackage.eNS_URI, PropertiesExtWidgetsReferencePackage.eINSTANCE);

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("odesign", new XMIResourceFactoryImpl()); //$NON-NLS-1$

        ClassPathResource environmentClassPathResource = new ClassPathResource(ENVIRONMENT_ODESIGN_PATH);
        Resource environmentResource = new XMIResourceFactoryImpl().createResource(URI.createURI(ENVIRONMENT_ODESIGN_URI));
        try (InputStream environmentInputStream = environmentClassPathResource.getInputStream()) {
            environmentResource.load(environmentInputStream, new EMFResourceUtils().getFastXMILoadOptions());
            resourceSet.getResources().add(environmentResource);
        } catch (IOException exception) {
            this.logger.error(exception.getMessage(), exception);
        }

        URI uri = URI.createURI(fileName);
        Resource resource = resourceSet.createResource(uri);

        try {
            resource.load(inputStream, new EMFResourceUtils().getFastXMILoadOptions());
        } catch (IOException exception) {
            this.logger.error(exception.getMessage(), exception);
        }

        // @formatter:off
        return resource.getContents().stream()
                .findFirst()
                .filter(Group.class::isInstance)
                .map(Group.class::cast);
        // @formatter:on
    }
}
