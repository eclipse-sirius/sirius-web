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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.utils.RectangleSide;

/**
 * Class representing border node on a parent side.
 *
 * @author lfasani
 */
public class BorderNodesOnSide {

    private final List<NodeLayoutData> borderNodes;

    private final RectangleSide side;

    BorderNodesOnSide(RectangleSide side, List<NodeLayoutData> borderNodes) {
        this.side = Objects.requireNonNull(side);
        this.borderNodes = Objects.requireNonNull(borderNodes);
    }

    public List<NodeLayoutData> getBorderNodes() {
        return this.borderNodes;
    }

    public RectangleSide getSide() {
        return this.side;
    }
}
