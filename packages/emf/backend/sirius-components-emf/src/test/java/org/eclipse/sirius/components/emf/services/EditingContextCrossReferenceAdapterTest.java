/*******************************************************************************
 * Copyright (c) 2022 CEA.
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

import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.Test;

/**
 * Tests the EditingContextCrossReferenceAdapter class.
 *
 * @author lfasani
 */
public class EditingContextCrossReferenceAdapterTest {
    private EReference eReference;

    private EClass referencedClass;

    private Resource referencedResource;

    @Test
    public void testRemoveResource() {
        ResourceSet resourceSet = new ResourceSetImpl();
        this.createModel(resourceSet);

        EditingContextCrossReferenceAdapter editingContextCrossReferenceAdapter = new EditingContextCrossReferenceAdapter();
        resourceSet.eAdapters().add(editingContextCrossReferenceAdapter);

        assertThat(this.eReference.getEType()).isNotNull().isEqualTo(this.referencedClass);
        assertThat(editingContextCrossReferenceAdapter.getInverseReferences(this.referencedClass)).isNotEmpty().filteredOn(setting -> {
            Object object = setting.getEObject().eGet(setting.getEStructuralFeature());
            return object != null && object.equals(this.referencedClass);
        }).isNotEmpty();

        resourceSet.getResources().remove(this.referencedResource);

        this.checkClean(editingContextCrossReferenceAdapter);

    }

    @Test
    public void testRemoveReferencedEObject() {
        ResourceSet resourceSet = new ResourceSetImpl();
        this.createModel(resourceSet);

        EditingContextCrossReferenceAdapter editingContextCrossReferenceAdapter = new EditingContextCrossReferenceAdapter();
        resourceSet.eAdapters().add(editingContextCrossReferenceAdapter);

        assertThat(this.eReference.getEType()).isNotNull().isEqualTo(this.referencedClass);
        assertThat(editingContextCrossReferenceAdapter.getInverseReferences(this.referencedClass)).isNotEmpty().filteredOn(setting -> {
            Object object = setting.getEObject().eGet(setting.getEStructuralFeature());
            return object != null && object.equals(this.referencedClass);
        }).isNotEmpty();

        this.referencedClass.getEPackage().getEClassifiers().remove(this.referencedClass);

        this.checkClean(editingContextCrossReferenceAdapter);

    }

    /**
     * Check that the CrossReferenceAdapter and the proxies are correctly cleaned.
     */
    private void checkClean(EditingContextCrossReferenceAdapter editingContextCrossReferenceAdapter) {
        assertThat(this.eReference.getEType()).isNull();
        assertThat(editingContextCrossReferenceAdapter.getInverseReferences(this.eReference)).filteredOn(setting -> {
            Object object = setting.getEObject().eGet(setting.getEStructuralFeature());
            return object != null && object.equals(this.referencedClass);
        }).isEmpty();
    }

    private void createModel(ResourceSet resourceSet) {

        this.referencedResource = new JSONResourceFactory().createResource(UUID.randomUUID().toString());
        resourceSet.getResources().add(this.referencedResource);

        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        this.referencedResource.getContents().add(ePackage);
        this.referencedClass = EcoreFactory.eINSTANCE.createEClass();
        this.referencedClass.setName("referencedClass"); //$NON-NLS-1$
        ePackage.getEClassifiers().add(this.referencedClass);

        Resource referencingResource = new JSONResourceFactory().createResource(UUID.randomUUID().toString());
        resourceSet.getResources().add(referencingResource);

        ePackage = EcoreFactory.eINSTANCE.createEPackage();
        referencingResource.getContents().add(ePackage);
        EClass referencingClass = EcoreFactory.eINSTANCE.createEClass();
        ePackage.getEClassifiers().add(referencingClass);
        this.eReference = EcoreFactory.eINSTANCE.createEReference();
        referencingClass.getEReferences().add(this.eReference);
        this.eReference.setEType(this.referencedClass);
    }

}
