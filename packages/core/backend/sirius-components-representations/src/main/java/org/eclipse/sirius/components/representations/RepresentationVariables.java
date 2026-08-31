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
 * Contains some variables which are available in every single representations.
 *
 * @author sbegaudeau
 */
public final class RepresentationVariables {

    public static final Variable SELF = new Variable("self", Object.class, false, "The current element on which the operation is performed");

    private RepresentationVariables() {
        // Prevent instantiation
    }

}
