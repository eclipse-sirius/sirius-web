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

package org.eclipse.sirius.components.diagrams.components;

import java.util.UUID;

/**
 * Used to compute the identifier of a node.
 *
 * @author sbegaudeau
 */
public class NodeIdProvider {
    public String getNodeId(String parentElementId, String nodeDescriptionId, NodeContainmentKind containmentKind, String targetObjectId) {
        String rawIdentifier = parentElementId + containmentKind.toString() + nodeDescriptionId + targetObjectId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
    }
}
