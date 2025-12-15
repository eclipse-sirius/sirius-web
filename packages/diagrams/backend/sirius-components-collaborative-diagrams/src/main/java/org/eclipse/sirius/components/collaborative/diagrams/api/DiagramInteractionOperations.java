/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import org.eclipse.sirius.components.representations.Operation;

/**
 * Operations used while interacting with diagrams.
 *
 * @author sbegaudeau
 * @since v2026.1.0
 */
public class DiagramInteractionOperations {
    public static final String SINGLE_CLICK_TOOL = "Tool#singleClick";
    public static final Operation SINGLE_CLICK_TOOL_OPERATION = new Operation(SINGLE_CLICK_TOOL, "Used to execute a behavior on one diagram element");

    public static final String GROUP_TOOL = "Tool#group";
    public static final Operation GROUP_TOOL_OPERATION = new Operation(GROUP_TOOL, "Used to execute a behavior on multiple diagram elements at once");

    public static final String NODE_DROP = "Node#drop";
    public static final Operation NODE_DROP_OPERATION = new Operation(NODE_DROP, "Used to drop nodes on another node or on the background of the diagram");

    public static final String OBJECT_DROP = "Object#drop";
    public static final Operation OBJECT_DROP_OPERATION = new Operation(OBJECT_DROP, "Used to drop objects on a node or on the background of the diagram");
}
