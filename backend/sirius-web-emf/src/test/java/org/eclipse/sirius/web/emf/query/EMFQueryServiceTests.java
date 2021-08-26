/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.emf.services.EditingDomainFactory;
import org.eclipse.sirius.web.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.web.spring.collaborative.api.IQueryService;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntSuccessPayload;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link EMFQueryService}.
 *
 * @author fbarbin
 */
public class EMFQueryServiceTests {

    @Test
    public void testEMFQueryService() {

        IEditingContext editingContext = this.createEditingContext();

        IEditingContextEPackageService editingContextEPackageService = new IEditingContextEPackageService() {

            @Override
            public List<EPackage> getEPackages(UUID editingContextId) {
                return List.of(EcorePackage.eINSTANCE);
            }
        };
        IQueryService queryService = new EMFQueryService(editingContextEPackageService);

        QueryBasedIntInput input = new QueryBasedIntInput(UUID.randomUUID(), "aql:editingContext.eAllContents()->size()"); //$NON-NLS-1$
        IPayload payload = queryService.execute(editingContext, input);
        assertTrue(payload instanceof QueryBasedIntSuccessPayload);
        assertEquals(8, ((QueryBasedIntSuccessPayload) payload).getResult().intValue());
    }

    private IEditingContext createEditingContext() {
        Resource resource = this.createResourceWith4Elements();
        Resource resource2 = this.createResourceWith4Elements();
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create(resource, resource2);
        return new EditingContext(UUID.randomUUID(), editingDomain);
    }

    private Resource createResourceWith4Elements() {
        Resource resource = new XMIResourceImpl();
        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        EClass class1 = EcoreFactory.eINSTANCE.createEClass();
        EClass class2 = EcoreFactory.eINSTANCE.createEClass();
        EClass class3 = EcoreFactory.eINSTANCE.createEClass();
        ePackage.getEClassifiers().addAll(List.of(class1, class2, class3));
        resource.getContents().add(ePackage);
        return resource;
    }
}
