/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;

/**
 * Source nodes provider for a Sirius relation based edge.
 *
 * @author sbegaudeau
 */
public class RelationBasedSourceNodesProvider implements Function<VariableManager, List<Element>> {
    private final EdgeMapping edgeMapping;

    private final IIdentifierProvider identifierProvider;

    public RelationBasedSourceNodesProvider(EdgeMapping edgeMapping, IIdentifierProvider identifierProvider) {
        this.edgeMapping = Objects.requireNonNull(edgeMapping);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
    }

    @Override
    public List<Element> apply(VariableManager variableManager) {
        var optionalObject = variableManager.get(VariableManager.SELF, Object.class);
        var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
        if (optionalObject.isEmpty() || optionalCache.isEmpty()) {
            return List.of();
        }

        DiagramRenderingCache cache = optionalCache.get();
        Object object = optionalObject.get();

        // @formatter:off
        return cache.getElementsRepresenting(object).stream()
                .filter(this.isFromCompatibleSourceMapping())
                .filter(Objects::nonNull)
                .toList();
        // @formatter:on
    }

    private Predicate<Element> isFromCompatibleSourceMapping() {
        return nodeElement -> {
            return this.edgeMapping.getSourceMapping().stream().anyMatch(srcMapping -> this.isFromMapping(nodeElement, srcMapping));
        };
    }

    private boolean isFromMapping(Element nodeElement, DiagramElementMapping mapping) {
        if (nodeElement.getProps() instanceof NodeElementProps) {
            NodeElementProps props = (NodeElementProps) nodeElement.getProps();
            return Objects.equals(this.identifierProvider.getIdentifier(mapping), props.getDescriptionId().toString());
        }
        return false;
    }
}
