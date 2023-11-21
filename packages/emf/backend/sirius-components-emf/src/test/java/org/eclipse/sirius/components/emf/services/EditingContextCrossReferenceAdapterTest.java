/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
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
        this.createModelsForRemove(resourceSet);

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
        this.createModelsForRemove(resourceSet);

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

    @Test
    public void testMoveEObject() {
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource resource = this.createModelForMove(resourceSet);

        EditingContextCrossReferenceAdapter editingContextCrossReferenceAdapter = new EditingContextCrossReferenceAdapter();
        resourceSet.eAdapters().add(editingContextCrossReferenceAdapter);

        EPackage ePackageRoot = resource.getContents().stream()
            .filter(EPackage.class::isInstance)
            .map(EPackage.class::cast)
            .filter(ePkg -> "ePackageRoot".equals(ePkg.getName()))
            .findFirst()
            .orElse(null);
        assertNotNull(ePackageRoot);

        EPackage ePackageA = ePackageRoot.getESubpackages().stream()
                .filter(ePkg -> "ePackageA".equals(ePkg.getName()))
                .findFirst()
                .orElse(null);
        assertNotNull(ePackageA);

        EClass eClassA1 = ePackageA.getEClassifiers().stream()
                .filter(eClass -> "eClassA1".equals(eClass.getName()))
                .filter(EClass.class::isInstance)
                .map(EClass.class::cast)
                .findFirst()
                .orElse(null);
        assertNotNull(eClassA1);
        assertFalse(eClassA1.getESuperTypes().isEmpty());

        EPackage ePackageB = ePackageRoot.getESubpackages().stream()
                .filter(ePkg -> "ePackageB".equals(ePkg.getName()))
                .findFirst()
                .orElse(null);
        assertNotNull(ePackageB);

        EPackage ePackageC = ePackageA.getESubpackages().stream()
                .filter(ePkg -> "ePackageC".equals(ePkg.getName()))
                .findFirst()
                .orElse(null);
        assertNotNull(ePackageC);

        EClassifier eClassC1 = ePackageC.getEClassifiers().stream()
                .filter(eClass -> "eClassC1".equals(eClass.getName()))
                .findFirst()
                .orElse(null);
        assertNotNull(eClassC1);

        ePackageB.getESubpackages().add(ePackageC);

        assertFalse(eClassA1.getESuperTypes().isEmpty());
        assertEquals(ePackageC, eClassC1.getEPackage());
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

    private void createModelsForRemove(ResourceSet resourceSet) {
        this.referencedResource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        resourceSet.getResources().add(this.referencedResource);

        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        this.referencedResource.getContents().add(ePackage);
        this.referencedClass = EcoreFactory.eINSTANCE.createEClass();
        this.referencedClass.setName("referencedClass");
        ePackage.getEClassifiers().add(this.referencedClass);

        Resource referencingResource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        resourceSet.getResources().add(referencingResource);

        ePackage = EcoreFactory.eINSTANCE.createEPackage();
        referencingResource.getContents().add(ePackage);
        EClass referencingClass = EcoreFactory.eINSTANCE.createEClass();
        ePackage.getEClassifiers().add(referencingClass);
        this.eReference = EcoreFactory.eINSTANCE.createEReference();
        referencingClass.getEStructuralFeatures().add(this.eReference);
        this.eReference.setEType(this.referencedClass);
    }

    private Resource createModelForMove(ResourceSet resourceSet) {
        Resource  resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        resourceSet.getResources().add(resource);

        EPackage ePackageRoot = EcoreFactory.eINSTANCE.createEPackage();
        ePackageRoot.setName("ePackageRoot");
        resource.getContents().add(ePackageRoot);

        EPackage ePackageA = EcoreFactory.eINSTANCE.createEPackage();
        ePackageA.setName("ePackageA");
        ePackageRoot.getESubpackages().add(ePackageA);

        EClass eClassA1 = EcoreFactory.eINSTANCE.createEClass();
        eClassA1.setName("eClassA1");
        ePackageA.getEClassifiers().add(eClassA1);

        EPackage ePackageB = EcoreFactory.eINSTANCE.createEPackage();
        ePackageB.setName("ePackageB");
        ePackageRoot.getESubpackages().add(ePackageB);

        EPackage ePackageC = EcoreFactory.eINSTANCE.createEPackage();
        ePackageC.setName("ePackageC");
        ePackageA.getESubpackages().add(ePackageC);

        EClass eClassC1 = EcoreFactory.eINSTANCE.createEClass();
        eClassC1.setName("eClassC1");
        ePackageC.getEClassifiers().add(eClassC1);
        eClassA1.getESuperTypes().add(eClassC1);

        return resource;
    }
}
