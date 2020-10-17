/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.util.Optional;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

/**
 * Unit tests of the label feature provider registry.
 *
 * @author arichard
 *
 */
public class LabelFeatureProviderRegistryTestCases {

    @Test
    public void testRegistry() {
        LabelFeatureProviderRegistry labelFeatureProviderRegistry = new LabelFeatureProviderRegistry();
        NoOpLabelFeatureProvider noOpLabelFeatureProvider = new NoOpLabelFeatureProvider();
        EPackage ePackage = EcorePackage.eINSTANCE;
        labelFeatureProviderRegistry.put(ePackage.getNsURI(), noOpLabelFeatureProvider);
        Optional<ILabelFeatureProvider> labelFeatureProvider = labelFeatureProviderRegistry.getLabelFeatureProvider(ePackage.getNsURI());
        assertThat(true).isEqualTo(labelFeatureProvider.isPresent());
        assertThat(noOpLabelFeatureProvider).isEqualTo(labelFeatureProvider.get());
    }
}
