/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.Map;
import java.util.Objects;

import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;

/**
 * The result of the diagram transformation with the raw ELK diagram and a cache of the values computed during the
 * transformation.
 *
 * @author sbegaudeau
 */
public class ELKConvertedDiagram {
    private final ElkNode elkDiagram;

    private final Map<String, ElkGraphElement> id2ElkGraphElements;

    public ELKConvertedDiagram(ElkNode elkDiagram, Map<String, ElkGraphElement> id2ElkGraphElements) {
        this.elkDiagram = Objects.requireNonNull(elkDiagram);
        this.id2ElkGraphElements = Objects.requireNonNull(id2ElkGraphElements);
    }

    public ElkNode getElkDiagram() {
        return this.elkDiagram;
    }

    public Map<String, ElkGraphElement> getId2ElkGraphElements() {
        return this.id2ElkGraphElements;
    }
}
