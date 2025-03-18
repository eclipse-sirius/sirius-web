/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
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

    private final IDiagramIdProvider diagramIdProvider;

    private final EdgeDescription edgeDescription;

    private final AQLInterpreter interpreter;

    public TargetProvider(IDiagramIdProvider diagramIdProvider, EdgeDescription edgeDescription, AQLInterpreter interpreter) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
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

        String expression = this.edgeDescription.getTargetExpression();
        List<Object> semanticCandidates = this.interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects().orElse(List.of());

        return semanticCandidates.stream()
                .flatMap(semanticObject-> cache.getElementsRepresenting(semanticObject).stream())
                .filter(this::isFromCompatibleTargetMapping)
                .filter(Objects::nonNull)
                .toList();
    }

    private boolean isFromCompatibleTargetMapping(Element diagramElement) {
        return this.edgeDescription.getTargetDescriptions().stream()
                .anyMatch(elementDescription -> this.isFromDescription(diagramElement, elementDescription));
    }

    private boolean isFromDescription(Element diagramElement, DiagramElementDescription description) {
        return diagramElement.getProps() instanceof NodeElementProps nodeElementProps && Objects.equals(this.diagramIdProvider.getId(description), nodeElementProps.getDescriptionId())
                || diagramElement.getProps() instanceof EdgeElementProps edgeElementProps && Objects.equals(this.diagramIdProvider.getId(description), edgeElementProps.getDescriptionId());
    }
}
