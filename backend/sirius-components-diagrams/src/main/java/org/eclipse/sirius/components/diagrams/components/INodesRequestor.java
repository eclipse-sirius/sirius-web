/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Node;

/**
 * Used to find some information on a specific set of nodes from a previous diagram.
 *
 * @author sbegaudeau
 */
public interface INodesRequestor {
    Optional<Node> getByTargetObjectId(String targetObjectId);
}
