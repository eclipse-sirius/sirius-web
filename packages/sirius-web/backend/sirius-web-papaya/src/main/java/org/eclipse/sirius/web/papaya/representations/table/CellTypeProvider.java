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

import java.util.Optional;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.TextfieldCellElementProps;

/**
 * Used to compute the type of the cell.
 *
 * @author sbegaudeau
 */
public class CellTypeProvider implements BiFunction<VariableManager, Object, String> {
    @Override
    public String apply(VariableManager variableManager, Object columnTargetObject) {
        String type = "";
        Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
            EClassifier eType = eStructuralFeature.getEType();
            if (eStructuralFeature instanceof EAttribute) {
                if (EcorePackage.Literals.EBOOLEAN.equals(eType) || EcorePackage.Literals.EBOOLEAN_OBJECT.equals(eType)) {
                    type = CheckboxCellElementProps.TYPE;
                } else if (eType instanceof EEnum) {
                    type = SelectCellElementProps.TYPE;
                }
            } else if (eStructuralFeature instanceof EReference eReference) {
                if (eReference.isMany()) {
                    type = MultiSelectCellElementProps.TYPE;
                } else {
                    type = SelectCellElementProps.TYPE;
                }
            }
            if (type.isEmpty()) {
                type = TextfieldCellElementProps.TYPE;
            }
        }
        return type;
    }
}
