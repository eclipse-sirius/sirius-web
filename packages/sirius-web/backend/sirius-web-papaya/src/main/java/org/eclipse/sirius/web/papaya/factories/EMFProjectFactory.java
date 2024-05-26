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
 * Used to create the EMF projects.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class EMFProjectFactory implements IObjectFactory {
    @Override
    public void create(IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Eclipse EMF");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var emf = this.emf();
        resource.getContents().add(emf);
    }

    private Project emf() {
        var emf = PapayaFactory.eINSTANCE.createProject();
        emf.setName("Eclipse Modeling Framework");

        var orgEclipseEmfCommonPackages = List.of(
                "org.eclipse.emf.common",
                "org.eclipse.emf.common.archive",
                "org.eclipse.emf.common.command",
                "org.eclipse.emf.common.notify",
                "org.eclipse.emf.common.notify.impl",
                "org.eclipse.emf.common.util"
        );
        var orgEclipseEmfCommon = new ComponentInitializer().initialize("org.eclipse.emf.common", "org.eclipse.emf.common", orgEclipseEmfCommonPackages::contains);

        var orgEclipseEmfEditPackages = List.of(
                "org.eclipse.emf.edit",
                "org.eclipse.emf.edit.command",
                "org.eclipse.emf.edit.domain",
                "org.eclipse.emf.edit.provider",
                "org.eclipse.emf.edit.provider.resource",
                "org.eclipse.emf.edit.tree",
                "org.eclipse.emf.edit.tree.impl",
                "org.eclipse.emf.edit.tree.provider",
                "org.eclipse.emf.edit.tree.util"
        );
        var orgEclipseEmfEdit = new ComponentInitializer().initialize("org.eclipse.emf.edit", "org.eclipse.emf.edit", orgEclipseEmfEditPackages::contains);

        var orgEclipseEmfEcorePackages = List.of(
                "org.eclipse.emf.ecore",
                "org.eclipse.emf.ecore.impl",
                "org.eclipse.emf.ecore.plugin",
                "org.eclipse.emf.ecore.resource",
                "org.eclipse.emf.ecore.resource.impl",
                "org.eclipse.emf.ecore.util",
                "org.eclipse.emf.ecore.xml",
                "org.eclipse.emf.ecore.xml.namespace",
                "org.eclipse.emf.ecore.xml.namespace.impl",
                "org.eclipse.emf.ecore.xml.namespace.util",
                "org.eclipse.emf.ecore.xml.type",
                "org.eclipse.emf.ecore.xml.type.impl",
                "org.eclipse.emf.ecore.xml.type.internal",
                "org.eclipse.emf.ecore.xml.type.util"
        );
        var orgEclipseEmfEcore = new ComponentInitializer().initialize("org.eclipse.emf.ecore", "org.eclipse.emf.ecore", orgEclipseEmfEcorePackages::contains);

        var orgEclipseEmfEcoreEditPackages = List.of(
                "org.eclipse.emf.ecore.provider",
                "org.eclipse.emf.ecore.provider.annotation"
        );
        var orgEclipseEmfEcoreEdit = new ComponentInitializer().initialize("org.eclipse.emf.ecore.edit", "org.eclipse.emf.ecore.edit", orgEclipseEmfEcoreEditPackages::contains);
        emf.getComponents().addAll(List.of(
                orgEclipseEmfCommon,
                orgEclipseEmfEcoreEdit,
                orgEclipseEmfEcore,
                orgEclipseEmfEdit
        ));
        return emf;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
        var orgEclipseEmfCommonComponent = eObjectIndexer.getComponent("org.eclipse.emf.common");

        var orgEclipseEmfEcoreComponent = eObjectIndexer.getComponent("org.eclipse.emf.ecore");
        orgEclipseEmfEcoreComponent.getDependencies().add(orgEclipseEmfCommonComponent);

        var orgEclipseEmfEditComponent = eObjectIndexer.getComponent("org.eclipse.emf.edit");
        orgEclipseEmfEditComponent.getDependencies().addAll(List.of(orgEclipseEmfCommonComponent, orgEclipseEmfEcoreComponent));

        var orgEclipseEmfEcoreEditComponent = eObjectIndexer.getComponent("org.eclipse.emf.ecore.edit");
        orgEclipseEmfEcoreEditComponent.getDependencies().addAll(List.of(orgEclipseEmfEcoreComponent, orgEclipseEmfEditComponent));
    }
}
