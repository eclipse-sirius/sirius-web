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
import org.eclipse.sirius.web.papaya.factories.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.api.IObjectFactory;

/**
 * Used to create the Spring project.
 *
 * @author sbegaudeau
 */
public class SpringProjectFactory implements IObjectFactory {
    @Override
    public void create(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Spring Projects");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var springFramework = new SpringFrameworkProjectFactory().create(eObjectIndexer);
        var springDataCommons = new SpringDataCommonsProjectFactory().create(eObjectIndexer);

        var springProjects = List.of(springFramework, springDataCommons);
        resource.getContents().addAll(springProjects);
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        new SpringFrameworkProjectFactory().link(eObjectIndexer);
        new SpringDataCommonsProjectFactory().link(eObjectIndexer);
    }
}
