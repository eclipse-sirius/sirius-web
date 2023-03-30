/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

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
 * Copied from org.eclipse.sirius.components.compatibility.diagrams.RelationBasedSemanticElementsProvider.
 *
 * @author pcdavid
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
        for (Element nodeElement : cache.getNodeToObject().keySet()) {
            if (nodeElement.getProps() instanceof NodeElementProps) {
                NodeElementProps props = (NodeElementProps) nodeElement.getProps();
                if (this.sourceNodeDescriptionIds.contains(props.getDescriptionId())) {
                    Object object = cache.getNodeToObject().get(nodeElement);
                    objects.add(object);
                }
            }
        }

        return objects;
    }

}
