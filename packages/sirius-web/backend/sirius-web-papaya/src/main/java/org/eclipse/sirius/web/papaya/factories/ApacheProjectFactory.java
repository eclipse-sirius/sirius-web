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
 * Used to create Apache projects.
 *
 * @author sbegaudeau
 */
public class ApacheProjectFactory implements IObjectFactory {
    @Override
    public void create(IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Apache");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var apacheCommons = this.apacheCommons();
        resource.getContents().add(apacheCommons);
    }

    private Project apacheCommons() {
        var apacheCommons = PapayaFactory.eINSTANCE.createProject();
        apacheCommons.setName("Apache Commons");

        var apacheCommonsCollections4Component = new ComponentInitializer().initialize("commons-collections4", "org.apache.commons.collections4", packageName -> packageName.startsWith("org.apache.commons.collections4"));

        apacheCommons.getComponents().addAll(List.of(apacheCommonsCollections4Component));
        return apacheCommons;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
        // Nothing needed for now
    }
}
