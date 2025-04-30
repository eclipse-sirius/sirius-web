/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.operations;

import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.view.emf.operations.api.IAddExecutor;
import org.springframework.stereotype.Service;

/**
 * Used to perform the addition of a value in an object.
 *
 * @author sbegaudeau
 */
@Service
public class AddExecutor implements IAddExecutor {
    @Override
    public Object eAdd(EObject instance, String featureName, Object value) {
        var eStructuralFeature = instance.eClass().getEStructuralFeature(featureName);
        if (eStructuralFeature == null) {
            return null;
        }

        if (eStructuralFeature.isMany()) {
            Object object = instance.eGet(eStructuralFeature);
            if (object instanceof Collection objectCollection && value instanceof Collection valueCollection) {
                objectCollection.addAll(valueCollection);
            } else if (object instanceof Collection objectCollection && value != null) {
                objectCollection.add(value);
            }
        } else {
            this.eSet(instance, eStructuralFeature, value);
        }

        return instance;
    }

    private Object eSet(EObject instance, EStructuralFeature eStructuralFeature, Object value) {
        var isEEnum = eStructuralFeature.getEType() instanceof EEnum;
        var isStringValue = value instanceof String;
        var isIntegerValue = value instanceof Integer;

        if (!isEEnum || !isStringValue && !isIntegerValue) {
            if (eStructuralFeature instanceof EAttribute eAttribute && value instanceof String valueString) {
                EDataType attributeType = eAttribute.getEAttributeType();
                Object objectValue = EcoreUtil.createFromString(attributeType, valueString);
                instance.eSet(eStructuralFeature, objectValue);
            } else {
                instance.eSet(eStructuralFeature, value);
            }
        } else if (isEEnum) {
            EEnumLiteral literal = this.getEnumLiteral((EEnum) eStructuralFeature.getEType(), value);
            if (literal != null) {
                instance.eSet(eStructuralFeature, literal.getInstance());
            }
        }

        return instance;
    }

    private EEnumLiteral getEnumLiteral(EEnum eenum, Object value) {
        EEnumLiteral literal = null;
        if (value instanceof Integer valueInteger) {
            literal = eenum.getEEnumLiteral(valueInteger);
        } else if (value instanceof String valueString) {
            literal = eenum.getEEnumLiteral(valueString);
            if (literal == null) {
                literal = eenum.getEEnumLiteralByLiteral(valueString);
            }
        }

        return literal;
    }
}
