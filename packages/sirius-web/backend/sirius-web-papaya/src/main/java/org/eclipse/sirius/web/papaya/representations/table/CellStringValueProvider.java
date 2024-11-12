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
import java.util.Optional;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the string value of a cell.
 *
 * @author sbegaudeau
 */
public class CellStringValueProvider implements BiFunction<VariableManager, Object, String> {

    private final IIdentityService identityService;

    public CellStringValueProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public String apply(VariableManager variableManager, Object columnTargetObject) {
        String value = "";
        Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
            EObject eObject = optionalEObject.get();
            // check if self has the feature
            if (eObject.eClass().getEAllStructuralFeatures().contains(eStructuralFeature)) {
                Object objectValue = eObject.eGet(eStructuralFeature);
                if (eStructuralFeature instanceof EReference eReference) {
                    if (!eReference.isMany() && !eReference.isContainment()) {
                        value = this.identityService.getId(objectValue);
                    }
                } else if (objectValue != null) {
                    value = objectValue.toString();
                }
            }
        }
        if (value == null) {
            value = "";
        }
        return value;
    }
}
