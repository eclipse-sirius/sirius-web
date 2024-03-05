/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.emf.forms;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Utility class used to provide a label for a structural feature in the variable manager.
 *
 * @author sbegaudeau
 */
public class EStructuralFeatureLabelProvider implements Function<VariableManager, String> {

    private final String featureVariableName;

    private final AdapterFactory adapterFactory;

    public EStructuralFeatureLabelProvider(String featureVariableName, AdapterFactory adapterFactory) {
        this.featureVariableName = Objects.requireNonNull(featureVariableName);
        this.adapterFactory = Objects.requireNonNull(adapterFactory);
    }

    @Override
    public String apply(VariableManager variableManager) {
        Object object = variableManager.getVariables().get(VariableManager.SELF);
        Object feature = variableManager.getVariables().get(this.featureVariableName);

        if (object instanceof EObject eObject && feature instanceof EStructuralFeature eStructuralFeature) {
            Adapter adapter = this.adapterFactory.adapt(eObject, IItemPropertySource.class);

            if (adapter instanceof IItemPropertySource itemPropertySource) {
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
                if (descriptor != null) {
                    return descriptor.getDisplayName(eStructuralFeature);
                }
            }
        }
        return "";
    }

}
