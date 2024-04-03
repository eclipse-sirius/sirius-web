/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.api;

/**
 * Description of the changes performed by the event handler.
 *
 * @author sbegaudeau
 */
public final class ChangeKind {

    public static final String NOTHING = "NOTHING";

    public static final String REPRESENTATION_CREATION = "REPRESENTATION_CREATION";

    public static final String REPRESENTATION_DELETION = "REPRESENTATION_DELETION";

    public static final String REPRESENTATION_RENAMING = "REPRESENTATION_RENAMING";

    public static final String SEMANTIC_CHANGE = "SEMANTIC_CHANGE";

    public static final String FOCUS_CHANGE = "FOCUS_CHANGE";

    public static final String REPRESENTATION_TO_DELETE = "REPRESENTATION_TO_DELETE";

    public static final String REPRESENTATION_TO_RENAME = "REPRESENTATION_TO_RENAME";

    public static final String RELOAD_REPRESENTATION = "RELOAD_REPRESENTATION";

    private ChangeKind() {
        // Prevent instantiation
    }
}
