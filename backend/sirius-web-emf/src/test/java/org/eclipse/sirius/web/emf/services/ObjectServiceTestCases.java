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
package org.eclipse.sirius.web.emf.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for the EMF-base {@link IObjectService} implementation.
 *
 * @author pcdavid
 */
@RunWith(JUnit4.class)
public class ObjectServiceTestCases {
    @Test
    public void testFindImagePathOnCompositeImage() {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(List.of(new EcoreItemProviderAdapterFactory()));
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
        ObjectService objectService = new ObjectService(composedAdapterFactory, new LabelFeatureProviderRegistry());
        EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
        String imagePath = objectService.getImagePath(attr);
        // @formatter:off
        assertThat(imagePath)
            .isNotNull()
            .endsWith("/icons/full/obj16/EAttribute.gif"); //$NON-NLS-1$
        // @formatter:on
    }

    @Test
    public void testGetIdOnEMFProxy() {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(List.of(new EcoreItemProviderAdapterFactory()));
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
        ObjectService objectService = new ObjectService(composedAdapterFactory, new LabelFeatureProviderRegistry());
        Resource resource = new XMIResourceImpl();
        resource.setURI(URI.createURI("test.xmi")); //$NON-NLS-1$
        EObject eObject = EcoreFactory.eINSTANCE.createEClass();
        resource.getContents().add(eObject);
        resource.unload();
        assertThat(eObject.eIsProxy()).isTrue();
        assertThat(objectService.getId(eObject)).isNotNull();
    }

}
