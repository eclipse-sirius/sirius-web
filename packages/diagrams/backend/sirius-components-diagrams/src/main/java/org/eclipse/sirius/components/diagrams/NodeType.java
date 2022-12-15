/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams;

/**
 * The various node types.
 *
 * @author sbegaudeau
 */
public final class NodeType {
    public static final String NODE_RECTANGLE = "node:rectangle";

    public static final String NODE_IMAGE = "node:image";

    public static final String NODE_ICON_LABEL = "node:icon-label";

    private NodeType() {
        // Prevent instantiation
    }
}
