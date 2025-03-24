/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the semantic elements of a relation based edge.
 *
 * @author sbegaudeau
 */
public class RelationBasedSemanticElementsProvider implements Function<VariableManager, List<?>> {

    private final List<String> sourceNodeDescriptionIds;

    public RelationBasedSemanticElementsProvider(List<String> sourceNodeDescriptionIds) {
        this.sourceNodeDescriptionIds = Objects.requireNonNull(sourceNodeDescriptionIds);
    }

    @Override
    public List<?> apply(VariableManager variableManager) {
        List<Object> objects = new ArrayList<>();

        var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
        if (optionalCache.isEmpty()) {
            return List.of();
        }

        DiagramRenderingCache cache = optionalCache.get();
        for (Element nodeElement : cache.getElementToObject().keySet()) {
            if (nodeElement.getProps() instanceof NodeElementProps) {
                NodeElementProps props = (NodeElementProps) nodeElement.getProps();
                if (this.sourceNodeDescriptionIds.contains(props.getDescriptionId())) {
                    Object object = cache.getElementToObject().get(nodeElement);
                    objects.add(object);
                }
            }
        }

        return objects;
    }

}
