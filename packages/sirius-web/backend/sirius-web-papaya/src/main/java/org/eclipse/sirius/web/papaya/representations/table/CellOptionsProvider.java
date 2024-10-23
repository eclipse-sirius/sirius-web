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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the options of the cell.
 *
 * @author sbegaudeau
 */
public class CellOptionsProvider implements BiFunction<VariableManager, Object, List<Object>> {

    private final ComposedAdapterFactory composedAdapterFactory;

    public CellOptionsProvider(ComposedAdapterFactory composedAdapterFactory) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }

    @Override
    public List<Object> apply(VariableManager variableManager, Object columnTargetObject) {
        List<Object> options = new ArrayList<>();
        Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
            EObject eObject = optionalEObject.get();
            EClassifier eType = eStructuralFeature.getEType();
            if (eType instanceof EEnum eEnum) {
                options.addAll(eEnum.getELiterals());
            } else {
                Object adapter = this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class);
                if (adapter instanceof IItemPropertySource itemPropertySource) {
                    IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
                    if (descriptor != null) {
                        return descriptor.getChoiceOfValues(eObject).stream()
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
                    }
                }
            }
        }
        return options;
    }
}
