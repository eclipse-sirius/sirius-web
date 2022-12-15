/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.compatibility.services.diagrams;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.api.IInitialDirectEditElementLabelProvider;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;

/**
 * The {@link IInitialDirectEditElementLabelProvider} for the compatibility layer.
 *
 * @author gcoutable
 */
@Service
public class CompatibilityInitialDirectEditElementLabelProvider implements IInitialDirectEditElementLabelProvider {

    private final IIdentifierProvider identifierProvider;

    public CompatibilityInitialDirectEditElementLabelProvider(IIdentifierProvider identifierProvider) {
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.identifierProvider.findVsmElementId(diagramDescription.getId()).isPresent();
    }

    @Override
    public String getInitialDirectEditElementLabel(Object graphicalElement, Diagram diagram, IEditingContext editingContext) {

        // @formatter:off
        return this.mapDiagramElementToLabel(graphicalElement)
            .orElse("");
        // @formatter:on
    }

    private Optional<String> mapDiagramElementToLabel(Object object) {
        Optional<String> targetObjectId = Optional.empty();
        if (object instanceof Node) {
            targetObjectId = Optional.of(((Node) object).getLabel().getText());
        } else if (object instanceof Edge) {
            targetObjectId = Optional.of(((Edge) object).getCenterLabel().getText());
        }
        return targetObjectId;
    }

}
