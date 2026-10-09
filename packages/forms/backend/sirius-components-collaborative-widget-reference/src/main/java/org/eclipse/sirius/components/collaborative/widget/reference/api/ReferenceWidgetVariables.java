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

package org.eclipse.sirius.components.collaborative.widget.reference.api;

import org.eclipse.sirius.components.representations.Variable;

/**
 * Contains some variables which are available for reference widgets.
 *
 * @author tgiraudet
 */
public final class ReferenceWidgetVariables {

    public static final Variable REFERENCE = new Variable("reference", Object.class, false, "The reference handled by the reference widget");

    public static final Variable NEW_VALUE = new Variable("newValue", Object.class, false, "The chosen values to be assigned to the owner reference");

    // We expose both single value and multi values as the same variable "newValue"
    public static final Variable NEW_VALUES = new Variable("newValue", Object.class, true, "The chosen value to be assigned to the owner reference");

    private ReferenceWidgetVariables() {
        // Prevent instantiation
    }
}
