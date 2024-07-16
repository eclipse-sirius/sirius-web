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
package org.eclipse.sirius.web.services.diagrams;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Java Services for {@link CustomVariableDiagramDescriptionProvider} related tests.
 *
 * @author arichard
 */
public class CustomVariableJavaServices {

    public EObject setMyCustomVariableToFalse(EObject self, VariableManager variableManager) {
        var currentVariableManager = variableManager;
        while (currentVariableManager != null && !currentVariableManager.hasVariable("myCustomVariable")) {
            currentVariableManager = currentVariableManager.getParent();
        }
        if (currentVariableManager != null) {
            currentVariableManager.put("myCustomVariable", false);
        }
        return self;
    }
}
