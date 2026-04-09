/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDecoratorDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDecoratorDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SemanticDecoratorDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Finds the requested node decorators.
 *
 * @author gdaniel
 */
public class DecoratorDescriptionRequestor implements IDecoratorDescriptionRequestor {

    private final List<IDecoratorDescription> decoratorDescriptions;

    public DecoratorDescriptionRequestor(DiagramDescription diagramDescription) {
        this.decoratorDescriptions = diagramDescription.getDecoratorDescriptions();
    }

    @Override
    public List<IDecoratorDescription> find(NodeDescription nodeDescription, VariableManager variableManager) {
        return this.decoratorDescriptions.stream()
                .filter(decoratorDescription -> this.matchesNodeDescription(decoratorDescription, nodeDescription)
                        || this.matchesTargetObject(decoratorDescription, variableManager))
                .toList();
    }

    private boolean matchesNodeDescription(IDecoratorDescription decoratorDescription, NodeDescription nodeDescription) {
        boolean result = false;
        if (decoratorDescription instanceof NodeDecoratorDescription nodeDecoratorDescription) {
            result = nodeDecoratorDescription.getNodeDescriptions().stream().anyMatch(decoratorNodeDescription -> Objects.equals(decoratorNodeDescription.getId(), nodeDescription.getId()));
        }
        return result;
    }

    private boolean matchesTargetObject(IDecoratorDescription decoratorDescription, VariableManager variableManager) {
        boolean result = false;
        if (decoratorDescription instanceof SemanticDecoratorDescription semanticDecoratorDescription) {
            result = semanticDecoratorDescription.getDomainTypePredicate().test(variableManager);
        }
        return result;
    }
}
