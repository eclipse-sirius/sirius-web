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
package org.eclipse.sirius.components.diagrams.tests.builder.node;

import java.util.Map;

import org.eclipse.sirius.components.diagrams.Node;

/**
 * Builder used to build a node.
 *
 * @param <T>
 *            Parent build type
 * @author gcoutable
 */
public interface NodeBuilder<T> {

    Node build(Map<String, String> targetObjectIdToNodeId);
}
