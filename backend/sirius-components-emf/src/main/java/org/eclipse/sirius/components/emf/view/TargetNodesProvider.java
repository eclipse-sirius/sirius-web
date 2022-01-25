/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.emf.view;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.DiagramElementDescription;

/**
 * The target nodes provider.
 *
 * @author pcdavid
 */
public class TargetNodesProvider implements Function<VariableManager, List<Element>> {

    private final Function<DiagramElementDescription, UUID> idProvider;

    private final org.eclipse.sirius.components.view.EdgeDescription edgeDescription;

    private final AQLInterpreter interpreter;

    public TargetNodesProvider(Function<DiagramElementDescription, UUID> idProvider, org.eclipse.sirius.components.view.EdgeDescription edgeDescription, AQLInterpreter interpreter) {
        this.idProvider = idProvider;
        this.edgeDescription = Objects.requireNonNull(edgeDescription);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    @Override
    public List<Element> apply(VariableManager variableManager) {

        var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
        if (optionalCache.isEmpty()) {
            return List.of();
        }

        DiagramRenderingCache cache = optionalCache.get();

        // @formatter:off
        Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), this.edgeDescription.getTargetNodesExpression());
        return result.asObjects().orElse(List.of()).stream()
                .flatMap(semanticObject-> cache.getElementsRepresenting(semanticObject).stream())
                .filter(this.isFromCompatibleTargetMapping())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // @formatter:on
    }

    private Predicate<Element> isFromCompatibleTargetMapping() {
        return nodeElement -> this.edgeDescription.getTargetNodeDescriptions().stream().anyMatch(nodeDescription -> this.isFromDescription(nodeElement, nodeDescription));
    }

    private boolean isFromDescription(Element nodeElement, DiagramElementDescription description) {
        if (nodeElement.getProps() instanceof NodeElementProps) {
            NodeElementProps props = (NodeElementProps) nodeElement.getProps();
            return Objects.equals(this.idProvider.apply(description), props.getDescriptionId());
        }
        return false;
    }
}
