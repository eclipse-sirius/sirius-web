/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;

/**
 * Finds the target elements for an edge. A target element is an already existing graphical element which:
 * <ul>
 * <li>is an instance of one of the the edge's possible target types;</li>
 * <li>represents one of the semantic target elements returned by the
 * {@link EdgeDescription#getTargetExpression()} ()}.</li>
 * </ul>
 * The implementation depends on the availability of the {@link DiagramRenderingCache} in the variables.
 *
 * @author pcdavid
 */
public class TargetProvider implements Function<VariableManager, List<Element>> {

    private final Set<String> targetDescriptionIds;

    private final EdgeDescription edgeDescription;

    private final AQLInterpreter interpreter;

    public TargetProvider(IDiagramIdProvider diagramIdProvider, EdgeDescription edgeDescription, AQLInterpreter interpreter) {
        Objects.requireNonNull(diagramIdProvider);
        this.edgeDescription = Objects.requireNonNull(edgeDescription);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.targetDescriptionIds = edgeDescription.getTargetDescriptions().stream()
                .map(diagramIdProvider::getId)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public List<Element> apply(VariableManager variableManager) {
        var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
        if (optionalCache.isEmpty()) {
            return List.of();
        }

        DiagramRenderingCache cache = optionalCache.get();

        String expression = this.edgeDescription.getTargetExpression();
        List<Object> semanticCandidates = this.interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects().orElse(List.of());

        return semanticCandidates.stream()
                .flatMap(semanticObject -> cache.getElementsRepresenting(semanticObject).stream())
                .filter(this::isFromCompatibleTargetMapping)
                .toList();
    }

    private boolean isFromCompatibleTargetMapping(Element diagramElement) {
        return diagramElement.getProps() instanceof NodeElementProps nodeElementProps && this.targetDescriptionIds.contains(nodeElementProps.getDescriptionId())
                || diagramElement.getProps() instanceof EdgeElementProps edgeElementProps && this.targetDescriptionIds.contains(edgeElementProps.getDescriptionId());
    }
}
