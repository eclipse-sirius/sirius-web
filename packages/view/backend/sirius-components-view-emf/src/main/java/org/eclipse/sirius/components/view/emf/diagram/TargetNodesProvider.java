/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.EdgeDescription;

/**
 * Finds the target nodes for an edge. A target node is an already existing graphical node which:
 * <ul>
 * <li>is an instance of one of the the edge's possible target types;</li>
 * <li>represents one of the semantic target elements returned by the
 * {@link EdgeDescription#getTargetNodesExpression()}.</li>
 * </ul>
 * The implementation depends on the availability of the {@link DiagramRenderingCache} in the variables.
 *
 * @author pcdavid
 */
public class TargetNodesProvider implements Function<VariableManager, List<Element>> {

    private final Function<DiagramElementDescription, UUID> idProvider;

    private final EdgeDescription edgeDescription;

    private final AQLInterpreter interpreter;

    public TargetNodesProvider(Function<DiagramElementDescription, UUID> idProvider, EdgeDescription edgeDescription, AQLInterpreter interpreter) {
        this.idProvider = Objects.requireNonNull(idProvider);
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
        String expression = this.edgeDescription.getTargetNodesExpression();
        List<Object> semanticCandidates = this.interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects().orElse(List.of());
        return semanticCandidates.stream()
                .flatMap(semanticObject-> cache.getElementsRepresenting(semanticObject).stream())
                .filter(this::isFromCompatibleTargetMapping)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // @formatter:on
    }

    private boolean isFromCompatibleTargetMapping(Element nodeElement) {
        // @formatter:off
        return this.edgeDescription.getTargetNodeDescriptions().stream()
                   .anyMatch(nodeDescription -> this.isFromDescription(nodeElement, nodeDescription));
        // @formatter:on
    }

    private boolean isFromDescription(Element nodeElement, DiagramElementDescription description) {
        if (nodeElement.getProps() instanceof NodeElementProps) {
            NodeElementProps props = (NodeElementProps) nodeElement.getProps();
            return Objects.equals(this.idProvider.apply(description), props.getDescriptionId());
        }
        return false;
    }
}
