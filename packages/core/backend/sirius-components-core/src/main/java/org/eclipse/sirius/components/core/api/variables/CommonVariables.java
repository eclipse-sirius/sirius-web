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

import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Contains some variables which are available in several representations.
 *
 * @author sbegaudeau
 */
public final class CommonVariables {

    public static final Variable SELF = new Variable("self", List.of(Object.class), "The current element on which the operation is performed");

    public static final Variable EDITING_CONTEXT = new Variable("editingContext", List.of(IEditingContext.class), "The editing context is an abstraction used to access all the semantic data");

    public static final Variable ENVIRONMENT = new Variable("environment", List.of(Environment.class), "The environment may contain some information on the application currently running");

    private CommonVariables() {
        // Prevent instantiation
    }
}
