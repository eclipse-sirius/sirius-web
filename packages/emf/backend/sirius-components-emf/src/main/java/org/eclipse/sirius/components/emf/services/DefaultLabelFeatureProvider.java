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
package org.eclipse.sirius.components.emf.services;

import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.services.api.IDefaultLabelFeatureProvider;
import org.springframework.stereotype.Service;

/**
 * Used to compute the default label feature of an EObject.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultLabelFeatureProvider implements IDefaultLabelFeatureProvider {

    @Override
    public Optional<EAttribute> getDefaultLabelEAttribute(EObject eObject) {
        Optional<EAttribute> optionalLabelEAttribute = Optional.empty();
        EList<EAttribute> allAttributes = eObject.eClass().getEAllAttributes();
        for (EAttribute eAttribute : allAttributes) {
            if (!eAttribute.isMany() && eAttribute.getEAttributeType().getInstanceClass() == String.class) {
                if ("name".equals(eAttribute.getName())) {
                    optionalLabelEAttribute = Optional.of(eAttribute);
                    break;
                } else if (optionalLabelEAttribute.isEmpty()) {
                    optionalLabelEAttribute = Optional.of(eAttribute);
                }
            }
        }
        return optionalLabelEAttribute;
    }
}
