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
package org.eclipse.sirius.web.diagrams.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.web.components.Element;

/**
 * Cache used during the rendering of a diagram.
 *
 * @author sbegaudeau
 */
public class DiagramRenderingCache {

    private final Map<UUID, List<Element>> nodeDescriptionIdToNodes = new HashMap<>();

    private final Map<Element, Object> nodeToObject = new LinkedHashMap<>();

    private final Map<Object, List<Element>> objectToNodes = new LinkedHashMap<>();

    public void put(UUID nodeDescriptionId, Element nodeElement) {
        this.nodeDescriptionIdToNodes.computeIfAbsent(nodeDescriptionId, id -> new ArrayList<>()).add(nodeElement);
    }

    public void put(Object object, Element nodeElement) {
        this.nodeToObject.put(nodeElement, object);
        this.objectToNodes.computeIfAbsent(object, obj -> new ArrayList<>()).add(nodeElement);
    }

    public Map<UUID, List<Element>> getNodeDescriptionIdToNodes() {
        return this.nodeDescriptionIdToNodes;
    }

    public Map<Element, Object> getNodeToObject() {
        return this.nodeToObject;
    }

    public List<Element> getElementsRepresenting(Object semanticObject) {
        return this.objectToNodes.getOrDefault(semanticObject, Collections.emptyList());
    }

    public Map<Object, List<Element>> getObjectToNodes() {
        return this.objectToNodes;
    }
}
