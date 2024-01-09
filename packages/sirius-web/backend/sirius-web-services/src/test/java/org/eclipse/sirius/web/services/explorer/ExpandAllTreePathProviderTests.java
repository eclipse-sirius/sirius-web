/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.services.explorer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.documents.EditingDomainFactory;
import org.eclipse.sirius.web.services.explorer.api.IExplorerNavigationService;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the expandAllTreePath service.
 *
 * @author frouene
 */
class ExpandAllTreePathProviderTests {

    private EClass class1;

    @Test
    void testExpandAllTreePathProvider() {
        IObjectService objectService = new IObjectService.NoOp() {
            @Override
            public String getId(Object object) {
                if (object instanceof ENamedElement namedElement) {
                    return namedElement.getName();
                }
                return "";
            }

            @Override
            public List<Object> getContents(IEditingContext editingContext, String objectId) {
                return switch (objectId) {
                    case "root1" -> List.of(ExpandAllTreePathProviderTests.this.class1);
                    default -> List.of();
                };
            }

        };

        ExpandAllTreePathProvider expandAllTreePathProvider = new ExpandAllTreePathProvider(objectService, new IRepresentationService.NoOp(),
                new IExplorerNavigationService.NoOp());

        assertThat(expandAllTreePathProvider.canHandle(null)).isFalse();

        Tree tree = Tree.newTree("treeId")
                .descriptionId("")
                .label("label")
                .children(List.of())
                .build();
        assertThat(expandAllTreePathProvider.canHandle(tree)).isTrue();

        IEditingContext editingContext = this.createEditingContext();
        ExpandAllTreePathInput input = new ExpandAllTreePathInput(UUID.randomUUID(), "editingContextId", "representationId", "documentItemId");

        IPayload payload = expandAllTreePathProvider.handle(editingContext, tree, input);

        assertThat(payload).isInstanceOf(ExpandAllTreePathSuccessPayload.class);
        assertThat(((ExpandAllTreePathSuccessPayload) payload).treePath().getMaxDepth()).isEqualTo(2);
    }

    private EditingContext createEditingContext() {
        Resource resource = this.createResourceWith4Elements();
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        editingDomain.getResourceSet().getResources().add(resource);
        return new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());
    }

    private Resource createResourceWith4Elements() {
        Map<String, EObject> cache = new HashMap<>();

        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        ePackage.setName("root1");
        UUID ePackageUUID = UUID.nameUUIDFromBytes("ePackage".getBytes());
        ePackage.eAdapters().add(new IDAdapter(ePackageUUID));
        cache.put(ePackageUUID.toString(), ePackage);

        this.class1 = EcoreFactory.eINSTANCE.createEClass();
        this.class1.setName("content1");
        UUID class1UUID = UUID.nameUUIDFromBytes("class1".getBytes());
        this.class1.eAdapters().add(new IDAdapter(class1UUID));
        cache.put(class1UUID.toString(), ePackage);

        ePackage.getEClassifiers().add(this.class1);

        EPackage ePackage2 = EcoreFactory.eINSTANCE.createEPackage();
        ePackage2.setName("root2");
        UUID ePackage2UUID = UUID.nameUUIDFromBytes("ePackage2".getBytes());
        ePackage2.eAdapters().add(new IDAdapter(ePackage2UUID));
        cache.put(ePackage2UUID.toString(), ePackage2);

        XMIResource resource = new XMIResourceImpl() {
            @Override
            protected EObject getEObjectByID(String id) {
                return cache.get(id);
            }
        };
        resource.setURI(URI.createURI("/documentItemId"));
        resource.getContents().add(ePackage);
        resource.getContents().add(ePackage2);
        return resource;
    }

}
