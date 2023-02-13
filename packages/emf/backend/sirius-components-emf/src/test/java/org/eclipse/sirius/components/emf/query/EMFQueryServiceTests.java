/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.emf.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.IQueryService;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedIntSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.EditingDomainFactory;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link EMFQueryService}.
 *
 * @author fbarbin
 */
public class EMFQueryServiceTests {

    @Test
    public void testEMFQueryServiceAllContents() {
        IEditingContext editingContext = this.createEditingContext();

        IEditingContextEPackageService editingContextEPackageService = new IEditingContextEPackageService() {
            @Override
            public List<EPackage> getEPackages(String editingContextId) {
                return List.of(EcorePackage.eINSTANCE);
            }
        };
        IQueryService queryService = new EMFQueryService(editingContextEPackageService, List.of());

        QueryBasedIntInput input = new QueryBasedIntInput(UUID.randomUUID(), "aql:editingContext.allContents()->size()", Map.of());
        IPayload payload = queryService.execute(editingContext, input);
        assertTrue(payload instanceof QueryBasedIntSuccessPayload);
        assertEquals(8, ((QueryBasedIntSuccessPayload) payload).getResult());
    }

    @Test
    public void testEMFQueryServiceContents() {
        IEditingContext editingContext = this.createEditingContext();

        IEditingContextEPackageService editingContextEPackageService = new IEditingContextEPackageService() {

            @Override
            public List<EPackage> getEPackages(String editingContextId) {
                return List.of(EcorePackage.eINSTANCE);
            }
        };
        IQueryService queryService = new EMFQueryService(editingContextEPackageService, List.of());

        QueryBasedIntInput input = new QueryBasedIntInput(UUID.randomUUID(), "aql:editingContext.contents()->size()", Map.of());
        IPayload payload = queryService.execute(editingContext, input);
        assertTrue(payload instanceof QueryBasedIntSuccessPayload);
        assertEquals(2, ((QueryBasedIntSuccessPayload) payload).getResult());
    }

    @Test
    public void testEMFQueryServiceGetObjectById() {
        EditingContext editingContext = this.createEditingContext();

        IEditingContextEPackageService editingContextEPackageService = new IEditingContextEPackageService() {
            @Override
            public List<EPackage> getEPackages(String editingContextId) {
                return List.of(EcorePackage.eINSTANCE);
            }
        };

        IQueryService queryService = new EMFQueryService(editingContextEPackageService, List.of());

        // @formatter:off
        EObject eObjectToRetrieve = editingContext.getDomain().getResourceSet()
                .getResources().get(0)
                .getContents().get(0);

        Optional<IDAdapter> optionalIDAdapter = eObjectToRetrieve.eAdapters().stream()
                .filter(IDAdapter.class::isInstance)
                .map(IDAdapter.class::cast)
                .findFirst();

        String id = optionalIDAdapter.map(IDAdapter::getId).map(Object::toString).orElse("");
        // @formatter:on

        QueryBasedObjectInput input = new QueryBasedObjectInput(UUID.randomUUID(), "aql:editingContext.getObjectById('" + id + "')", Map.of());
        IPayload payload = queryService.execute(editingContext, input);

        assertTrue(payload instanceof QueryBasedObjectSuccessPayload);
        assertEquals(eObjectToRetrieve, ((QueryBasedObjectSuccessPayload) payload).getResult());

        input = new QueryBasedObjectInput(UUID.randomUUID(), "aql:editingContext.getObjectById('" + id + "wrong')", Map.of());
        payload = queryService.execute(editingContext, input);
        assertTrue(payload instanceof ErrorPayload);
    }

    private EditingContext createEditingContext() {
        Resource resource = this.createResourceWith4Elements();
        Resource resource2 = this.createResourceWith4Elements();
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create(resource, resource2);
        return new EditingContext(UUID.randomUUID().toString(), editingDomain);
    }

    private Resource createResourceWith4Elements() {
        Map<String, EObject> cache = new HashMap<>();

        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        UUID ePackageUUID = UUID.nameUUIDFromBytes("ePackage".getBytes());
        ePackage.eAdapters().add(new IDAdapter(ePackageUUID));
        cache.put(ePackageUUID.toString(), ePackage);

        EClass class1 = EcoreFactory.eINSTANCE.createEClass();
        UUID class1UUID = UUID.nameUUIDFromBytes("class1".getBytes());
        class1.eAdapters().add(new IDAdapter(class1UUID));
        cache.put(class1UUID.toString(), ePackage);

        EClass class2 = EcoreFactory.eINSTANCE.createEClass();
        UUID class2UUID = UUID.nameUUIDFromBytes("class2".getBytes());
        class2.eAdapters().add(new IDAdapter(class2UUID));
        cache.put(class2UUID.toString(), ePackage);

        EClass class3 = EcoreFactory.eINSTANCE.createEClass();
        UUID class3UUID = UUID.nameUUIDFromBytes("class3".getBytes());
        class3.eAdapters().add(new IDAdapter(class3UUID));
        cache.put(class3UUID.toString(), ePackage);

        ePackage.getEClassifiers().addAll(List.of(class1, class2, class3));

        XMIResource resource = new XMIResourceImpl() {
            @Override
            protected EObject getEObjectByID(String id) {
                return cache.get(id);
            }
        };
        resource.getContents().add(ePackage);
        return resource;
    }
}
