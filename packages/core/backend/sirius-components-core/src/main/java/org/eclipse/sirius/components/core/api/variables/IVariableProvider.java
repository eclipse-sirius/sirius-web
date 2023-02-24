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
package org.eclipse.sirius.components.core.api.variables;

import java.util.List;

/**
 * Used to provide the list of variables accessible for an operation.
 *
 * @author sbegaudeau
 */
public interface IVariableProvider {
    List<Variable> getVariables(String operation);

    default List<Variable> noVariables() {
        return List.of();
    }
}
