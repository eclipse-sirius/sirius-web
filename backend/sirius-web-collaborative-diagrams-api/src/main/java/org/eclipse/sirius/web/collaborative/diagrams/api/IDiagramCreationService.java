/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.collaborative.diagrams.api;

import java.util.Optional;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;

/**
 * Service used to create diagrams from scratch or from an existing diagram.
 *
 * @author sbegaudeau
 */
public interface IDiagramCreationService {

    /**
     * Creates a new diagram using the given parameters.
     *
     * @param label
     *            The label of the diagram
     * @param targetObject
     *            The object used as the target
     * @param diagramDescription
     *            The description of the diagram
     * @param editingContext
     *            The editing context
     * @return A new diagram properly layouted and saved in the data store
     */
    Diagram create(String label, Object targetObject, DiagramDescription diagramDescription, IEditingContext editingContext);

    /**
     * Refresh an existing diagram.
     *
     * <p>
     * Refreshing a diagram since to always be possible but it may not be the case. In some situation, the semantic
     * element on which the previous diagram has been created may not exist anymore and thus we can return an empty
     * optional if we are unable to create the new diagram.
     * </p>
     *
     * @param editingContext
     *            The editing context
     * @param diagramContext
     *            The diagram context
     * @return An updated diagram if we have been able to refresh it.
     */
    Optional<Diagram> refresh(IEditingContext editingContext, IDiagramContext diagramContext);

}
