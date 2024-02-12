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
package org.eclipse.sirius.components.emf.services;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

import java.util.List;

/**
 * Tests for the EMF-base {@link org.eclipse.sirius.components.core.api.IDefaultLabelService} implementation.
 *
 * @author pcdavid
 */
public class DefaultLabelServiceTests {
    @Test
    public void testFindImagePathOnCompositeImage() {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(List.of(new EcoreItemProviderAdapterFactory()));
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
        DefaultLabelService labelService = new DefaultLabelService(new LabelFeatureProviderRegistry(), composedAdapterFactory);
        EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
        List<String> imagePath = labelService.getImagePath(attr);
        assertThat(imagePath).hasSize(1);
        assertThat(imagePath.get(0))
                .endsWith("/icons/full/obj16/EAttribute.gif");
    }
}
