/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.components.emf.compatibility.properties;

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

    private String featureVariableName;

    private AdapterFactory adapterFactory;

    public EStructuralFeatureLabelProvider(String featureVariableName, AdapterFactory adapterFactory) {
        this.featureVariableName = Objects.requireNonNull(featureVariableName);
        this.adapterFactory = Objects.requireNonNull(adapterFactory);
    }

    @Override
    public String apply(VariableManager variableManager) {
        Object object = variableManager.getVariables().get(VariableManager.SELF);
        Object feature = variableManager.getVariables().get(this.featureVariableName);

        if (object instanceof EObject && feature instanceof EStructuralFeature) {
            EObject eObject = (EObject) object;
            EStructuralFeature eStructuralFeature = (EStructuralFeature) feature;

            Adapter adapter = this.adapterFactory.adapt(eObject, IItemPropertySource.class);
            if (adapter instanceof IItemPropertySource) {
                IItemPropertySource itemPropertySource = (IItemPropertySource) adapter;
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
                if (descriptor != null) {
                    String displayName = descriptor.getDisplayName(eStructuralFeature);
                    return displayName;
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

}
