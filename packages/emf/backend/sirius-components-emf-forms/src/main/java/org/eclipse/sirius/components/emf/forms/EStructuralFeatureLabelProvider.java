/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Utility class used to provide a label for a structural feature in the variable manager.
 *
 * @author sbegaudeau
 */
public class EStructuralFeatureLabelProvider implements Function<VariableManager, String> {

    private final String featureVariableName;

    public EStructuralFeatureLabelProvider(String featureVariableName) {
        this.featureVariableName = Objects.requireNonNull(featureVariableName);
    }

    @Override
    public String apply(VariableManager variableManager) {
        var object = variableManager.getVariables().get(VariableManager.SELF);
        var feature = variableManager.getVariables().get(this.featureVariableName);
        var optionalAdapterFactory = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getAdapterFactory);

        if (object instanceof EObject eObject && feature instanceof EStructuralFeature eStructuralFeature && optionalAdapterFactory.isPresent()) {
            Adapter adapter = optionalAdapterFactory.get().adapt(eObject, IItemPropertySource.class);

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
