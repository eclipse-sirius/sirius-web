/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.diagrams.layout.incremental.data;

import java.util.List;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;

/**
 * A mutable structure to store/update the diagram layout.
 *
 * @author wpiers
 */
public class DiagramLayoutData implements IContainerLayoutData {

    private String id;

    private Position position;

    private Size size;

    private List<NodeLayoutData> nodes;

    private List<EdgeLayoutData> edges;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Size getSize() {
        return this.size;
    }

    @Override
    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public List<NodeLayoutData> getChildrenNodes() {
        return this.nodes;
    }

    @Override
    public void setChildrenNodes(List<NodeLayoutData> children) {
        this.nodes = children;
    }

    public List<EdgeLayoutData> getEdges() {
        return this.edges;
    }

    public void setEdges(List<EdgeLayoutData> edges) {
        this.edges = edges;
    }

    @Override
    public Position getAbsolutePosition() {
        return this.position;
    }

}
