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
 * Used to create GraphQL projects.
 *
 * @author sbegaudeau
 */
public class GraphQLJavaProjectFactory implements IObjectFactory {
    @Override
    public void create(IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("GraphQL");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var graphQlJava = this.graphQlJava();
        resource.getContents().add(graphQlJava);
    }

    private Project graphQlJava() {
        var graphQlJava = PapayaFactory.eINSTANCE.createProject();
        graphQlJava.setName("GraphQL Java");

        var graphQlJavaComponent = new ComponentInitializer().initialize("graphql-java", "graphql", packageName -> packageName.startsWith("graphql"));

        graphQlJava.getElements().addAll(List.of(graphQlJavaComponent));
        return graphQlJava;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
        // Nothing needed for now
    }
}
