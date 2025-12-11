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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Utility class used to provide a choice of values for a structural feature and an object in the variable manager.
 *
 * @author lfasani
 */
public class EStructuralFeatureChoiceOfValueProvider implements Function<VariableManager, List<?>> {

    private final String featureVariableName;

    public EStructuralFeatureChoiceOfValueProvider(String featureVariableName) {
        this.featureVariableName = Objects.requireNonNull(featureVariableName);
    }

    @Override
    public List<?> apply(VariableManager variableManager) {
        var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
        var optionalEReference = variableManager.get(this.featureVariableName, EReference.class);
        var optionalAdapterFactory = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getAdapterFactory);

        if (optionalEObject.isPresent() && optionalEReference.isPresent() && optionalAdapterFactory.isPresent()) {
            EObject eObject = optionalEObject.get();
            EReference eReference = optionalEReference.get();

            Object adapter = optionalAdapterFactory.get().adapt(eObject, IItemPropertySource.class);
            if (adapter instanceof IItemPropertySource itemPropertySource) {
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eReference);
                if (descriptor != null) {
                    return descriptor.getChoiceOfValues(eObject).stream()
                            .filter(Objects::nonNull)
                            .toList();
                }
            }
        }

        return new ArrayList<>();
    }

}
