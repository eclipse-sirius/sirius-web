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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.emf.configuration.EMFConfiguration;
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
        ComposedAdapterFactory adapterFactory = new EMFConfiguration().composedAdapterFactory(List.of(new EcoreItemProviderAdapterFactory()));
        ObjectService objectService = new ObjectService(adapterFactory, new LabelFeatureProviderRegistry());
        EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
        String imagePath = objectService.getImagePath(attr);
        // @formatter:off
        assertThat(imagePath)
            .isNotNull()
            .startsWith("jar:") //$NON-NLS-1$
            .endsWith("!/icons/full/obj16/EAttribute.gif"); //$NON-NLS-1$
        // @formatter:on
    }
}
