/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.layoutdata;

/**
 * The layout data of a node.
 *
 * @author sbegaudeau
 */
public record NodeLayoutData(
        String id,
        Position position,
        Size size,
        boolean resizedByUser,
        int minimumWidth
) {
}
