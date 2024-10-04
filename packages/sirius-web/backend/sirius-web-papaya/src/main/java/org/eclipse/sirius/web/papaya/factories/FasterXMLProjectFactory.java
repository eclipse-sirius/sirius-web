/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.factories;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.web.papaya.factories.services.ComponentInitializer;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IObjectFactory;

/**
 * Used to create the Faster XML projects.
 *
 * @author sbegaudeau
 */
public class FasterXMLProjectFactory implements IObjectFactory {
    @Override
    public void create(IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Faster XML");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var jackson = this.jackson();
        resource.getContents().add(jackson);
    }

    private Project jackson() {
        var jackson = PapayaFactory.eINSTANCE.createProject();
        jackson.setName("Jackson");

        var jacksonCore = new ComponentInitializer().initialize("jackson-core", "com.fasterxml.jackson.core", packageName -> packageName.startsWith("com.fasterxml.jackson.core"));
        var jacksonDatabind = new ComponentInitializer().initialize("jackson-databind", "com.fasterxml.jackson.databind", packageName -> packageName.startsWith("com.fasterxml.jackson.databind"));

        jackson.getComponents().addAll(List.of(jacksonCore, jacksonDatabind));
        return jackson;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
        // Nothing for now
    }
}
