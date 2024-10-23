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

import java.util.function.Function;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the targetObjectId of the columns.
 *
 * @author sbegaudeau
 */
public class ColumnTargetObjectIdProvider implements Function<VariableManager, String> {
    @Override
    public String apply(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, EStructuralFeature.class)
                .map(eStructuralFeature -> eStructuralFeature.getEContainingClass().getEPackage().getNsPrefix() + "." + eStructuralFeature.getEContainingClass().getName() + "#" + eStructuralFeature.getName())
                .orElse("");
    }
}
