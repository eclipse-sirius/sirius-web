/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.graphql.api;

/**
 * Used to store the local context constants.
 *
 * @author sbegaudeau
 */
public final class LocalContextConstants {
    public static final String EDITING_CONTEXT_ID = "editingContextId";

    public static final String REPRESENTATION_ID = "representationId";

    private LocalContextConstants() {
        // Prevent instantiation
    }
}
