/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.representations;

/**
 * The description usage of a variable which can be used by various operations.
 *
 * @param variable The variable
 * @param optional Is the variable optional
 *
 * @author mcharfadi
 */
public record VariableUsage(Variable variable, boolean optional) {

    public VariableUsage(Variable variable) {
        this(variable, false);
    }

}
