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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

/**
 * Service used to create diagrams from scratch or from an existing diagram.
 *
 * @author sbegaudeau
 */
public interface IDiagramCreationService {

    /**
     * Creates a new diagram using the given parameters.
     *
     * @param targetObject
     *            The object used as the target
     * @param diagramDescription
     *            The description of the diagram
     * @param editingContext
     *            The editing context
     * @return A new diagram properly laid out and saved in the data store
     */
    Diagram create(IEditingContext editingContext, DiagramDescription diagramDescription, Object targetObject);

    /**
     * Refresh an existing diagram.
     *
     * <p>
     * Refreshing a diagram since to always be possible, but it may not be the case. In some situation, the semantic
     * element on which the previous diagram has been created may not exist anymore and thus we can return an empty
     * optional if we are unable to create the new diagram.
     * </p>
     *
     * @param editingContext
     *            The editing context
     * @param diagramContext
     *            The diagram context
     * @return An updated diagram if we have been able to refresh it.
     *
     * @technical-debt This method should ask for what it needs and not try to recompute it every time. As a result, it
     * should not return an optional.
     */
    Optional<Diagram> refresh(IEditingContext editingContext, DiagramContext diagramContext);

    /**
     * Used to update the layout of a diagram.
     *
     * @param editingContext The editing context
     * @param diagram The diagram
     * @param layoutDiagramInput The input used to update the diagram
     * @return The updated diagram
     *
     * @technical-debt This behavior should probably not exist in this service, but it will temporarily allow us to lower
     * the responsibilities of the diagram event processor. The entire method will probably be removed from this service
     * and transferred to a dedicated service in the near future.
     */
    @Deprecated(forRemoval = true)
    Diagram updateLayout(IEditingContext editingContext, Diagram diagram, LayoutDiagramInput layoutDiagramInput);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IDiagramCreationService {

        @Override
        public Diagram create(IEditingContext editingContext, DiagramDescription diagramDescription, Object targetObject) {
            return null;
        }

        @Override
        public Optional<Diagram> refresh(IEditingContext editingContext, DiagramContext diagramContext) {
            return Optional.empty();
        }

        @Override
        public Diagram updateLayout(IEditingContext editingContext, Diagram diagram, LayoutDiagramInput layoutDiagramInput) {
            return diagram;
        }
    }

}
