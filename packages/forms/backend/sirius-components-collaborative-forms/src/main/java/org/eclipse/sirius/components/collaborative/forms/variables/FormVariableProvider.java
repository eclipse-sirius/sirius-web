/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.variables;

import java.util.List;

import org.eclipse.sirius.components.core.api.variables.IVariableProvider;
import org.eclipse.sirius.components.core.api.variables.Variable;
import org.springframework.stereotype.Service;

/**
 * Used to provide the variables available for all form operations.
 *
 * @author frouene
 */
@Service
public class FormVariableProvider implements IVariableProvider {

    public static final Variable SELECTION = new Variable("selection", List.of(Object.class), "The selection of the workbench");

    @Override
    public List<Variable> getVariables(String operation) {
        return List.of();
    }
}
