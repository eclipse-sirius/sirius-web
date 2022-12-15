/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link EObjectIDManager}.
 *
 * @author gcoutable
 */
public class IDManagerTests {

    private EPackage ePackage;

    private EClass eClass;

    private ResourceSetImpl resourceSet;

    public IDManagerTests() {
        this.ePackage = EcoreFactory.eINSTANCE.createEPackage();
        this.ePackage.setName("test");
        this.ePackage.setNsPrefix("test");
        this.ePackage.setNsURI("http//:test");
        this.eClass = EcoreFactory.eINSTANCE.createEClass();
        this.eClass.setName("Test");
        this.ePackage.getEClassifiers().add(this.eClass);
        this.resourceSet = new ResourceSetImpl();
        this.resourceSet.getPackageRegistry().put(this.ePackage.getNsURI(), this.ePackage);
    }

    /**
     * Clears resource set between each tests.
     */
    @AfterEach
    public void tearDown() {
        this.resourceSet.getResources().clear();
    }

    @Test
    public void testMoveEObjectBetweenResource() {
        Resource r1 = this.createResource();
        Resource r2 = this.createResource();
        EObject eObject = this.createEOject();
        EObjectIDManager eObjectIDManager = new EObjectIDManager();

        r1.getContents().add(eObject);
        assertThat(eObjectIDManager.findId(eObject)).isNotEmpty();
        String eObjectId = eObjectIDManager.findId(eObject).get();

        r2.getContents().add(eObject);
        assertThat(eObjectIDManager.findId(eObject)).isNotEmpty();
        assertThat(eObjectIDManager.findId(eObject).get()).isEqualTo(eObjectId);
    }

    private EObject createEOject() {
        return this.ePackage.getEFactoryInstance().create(this.eClass);
    }

    private Resource createResource() {
        var options = new HashMap<>();
        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        JsonResourceImpl resource = new JsonResourceImpl(URI.createURI(""), options);
        this.resourceSet.getResources().add(resource);
        return resource;
    }

}
