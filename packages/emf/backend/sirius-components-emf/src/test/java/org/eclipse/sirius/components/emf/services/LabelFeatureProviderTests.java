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
package org.eclipse.sirius.components.emf.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the object service.
 *
 * @author arichard
 *
 */
public class LabelFeatureProviderTests {

    @Test
    public void testGetLabelFeature() {
        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        NoOpLabelFeatureProvider noOpLabelFeatureProvider = new NoOpLabelFeatureProvider() {
            @Override
            public Optional<EAttribute> getLabelEAttribute(EObject eObject) {
                hasBeenCalled.set(true);
                return null;
            }
        };

        noOpLabelFeatureProvider.getLabelEAttribute(null);
        assertThat(hasBeenCalled.get()).isTrue();
    }
}
