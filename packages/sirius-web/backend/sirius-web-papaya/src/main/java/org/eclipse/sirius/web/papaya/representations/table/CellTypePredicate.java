/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;
import org.eclipse.sirius.components.tables.elements.IconLabelCellElementProps;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.TextareaCellElementProps;
import org.eclipse.sirius.components.tables.elements.TextfieldCellElementProps;

/**
 * Used to compute the type of the cell.
 *
 * @author sbegaudeau
 */
public class CellTypePredicate {

    private String getCellType(VariableManager variableManager) {
        String type = "";
        Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
        Optional<EStructuralFeature> optionalColumnTargetObject = variableManager.get(ColumnDescription.COLUMN_TARGET_OBJECT, Object.class)
                .filter(EStructuralFeature.class::isInstance)
                .map(EStructuralFeature.class::cast);
        if (optionalEObject.isPresent() && optionalColumnTargetObject.isPresent()) {
            var eStructuralFeature = optionalColumnTargetObject.get();
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
            // description column should be multiline
            if ("description".equals(eStructuralFeature.getName())) {
                type = TextareaCellElementProps.TYPE;
            }
            if (type.isEmpty()) {
                type = TextfieldCellElementProps.TYPE;
            }
        }
        return type;
    }

    public Predicate<VariableManager> isTextfieldCell() {
        return (variableManager) -> this.getCellType(variableManager).equals(TextfieldCellElementProps.TYPE);
    }

    public Predicate<VariableManager> isTextareaCell() {
        return (variableManager) -> this.getCellType(variableManager).equals(TextareaCellElementProps.TYPE);
    }

    public Predicate<VariableManager> isCheckboxCell() {
        return (variableManager) -> this.getCellType(variableManager).equals(CheckboxCellElementProps.TYPE);
    }

    public Predicate<VariableManager> isSelectCell() {
        return (variableManager) -> this.getCellType(variableManager).equals(SelectCellElementProps.TYPE);
    }

    public Predicate<VariableManager> isMultiselectCell() {
        return (variableManager) -> this.getCellType(variableManager).equals(MultiSelectCellElementProps.TYPE);
    }

    public Predicate<VariableManager> isIconLabelCell() {
        return (variableManager) -> this.getCellType(variableManager).equals(IconLabelCellElementProps.TYPE);
    }
}
