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
package org.eclipse.sirius.components.compatibility.emf.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Utility class used to provide a choice of values for a structural feature and an object in the variable manager.
 *
 * @author lfasani
 */
public class EStructuralFeatureChoiceOfValueProvider implements Function<VariableManager, List<?>> {

    private String featureVariableName;

    private AdapterFactory adapterFactory;

    public EStructuralFeatureChoiceOfValueProvider(String featureVariableName, AdapterFactory adapterFactory) {
        this.featureVariableName = Objects.requireNonNull(featureVariableName);
        this.adapterFactory = Objects.requireNonNull(adapterFactory);
    }

    @Override
    public List<?> apply(VariableManager variableManager) {
        var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
        var optionalEReference = variableManager.get(this.featureVariableName, EReference.class);

        if (optionalEObject.isPresent() && optionalEReference.isPresent()) {
            EObject eObject = optionalEObject.get();
            EReference eReference = optionalEReference.get();

            Object adapter = this.adapterFactory.adapt(eObject, IItemPropertySource.class);
            if (adapter instanceof IItemPropertySource) {
                IItemPropertySource itemPropertySource = (IItemPropertySource) adapter;
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eReference);
                if (descriptor != null) {
                    // @formatter:off
                    List<Object> choiceOfValues = descriptor.getChoiceOfValues(eObject).stream()
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    // @formatter:on
                    return choiceOfValues;
                }
            }
        }

        return new ArrayList<>();
    }

}
