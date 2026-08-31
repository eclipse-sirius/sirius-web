/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Variable;

/**
 * Contains some variables which are available in several representations.
 *
 * @author sbegaudeau
 */
public final class CoreVariables {

    public static final Variable EDITING_CONTEXT = new Variable("editingContext", IEditingContext.class, false, "The editing context is an abstraction used to access all the semantic data");

    public static final Variable ENVIRONMENT = new Variable("environment", Environment.class, false, "The environment may contain some information on the application currently running");

    private CoreVariables() {
        // Prevent instantiation
    }
}
