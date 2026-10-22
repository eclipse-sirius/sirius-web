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

    public static final Variable REFERENCE_OWNER = new Variable("referenceOwner", Object.class, false, "The object owning the reference attached to the reference widget");

    public static final Variable REFERENCE = new Variable("reference", Object.class, false, "The reference handled by the reference widget");

    public static final Variable CREATION_CONTAINER = new Variable("creationContainer", Object.class, false, "The container chosen for the new instance");

    public static final Variable CREATION_REFERENCE = new Variable("creationReference", Object.class, false, "The containment reference chosen for the new instance, absent for a new root element");

    public static final Variable CREATION_TYPE = new Variable("creationType", Object.class, false, "The type chosen for the new instance");

    // We expose both single value and multi values as the same variable "newValue"
    public static final Variable NEW_VALUES = new Variable("newValues", Object.class, true, "The chosen values to be assigned to the reference owner");

    public static final Variable NEW_VALUE = new Variable("newValue", Object.class, false,
            "The chosen value to be assigned to the reference owner, deprecated in favor of `newValues` for `add` feature");

    public static final Variable VALUE_TO_REMOVE = new Variable("valueToRemove", Object.class, false, "The chosen value to remove from the reference owner");

    private ReferenceWidgetVariables() {
        // Prevent instantiation
    }
}
