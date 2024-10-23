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
package org.eclipse.sirius.web.papaya.representations.table;

import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;

/**
 * Used to compute the display name of an object.
 *
 * @author sbegaudeau
 */
public class DisplayNameProvider {

    private final ComposedAdapterFactory composedAdapterFactory;

    public DisplayNameProvider(ComposedAdapterFactory composedAdapterFactory) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }

    public String getDisplayName(EObject eObject, EStructuralFeature eStructuralFeature) {
        Adapter adapter = this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class);
        if (adapter instanceof IItemPropertySource itemPropertySource) {
            IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
            if (descriptor != null) {
                return descriptor.getDisplayName(eStructuralFeature);
            }
        }
        return eStructuralFeature.getName();
    }
}
