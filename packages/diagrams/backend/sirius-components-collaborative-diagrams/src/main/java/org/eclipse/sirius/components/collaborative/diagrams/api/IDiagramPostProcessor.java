/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;

/**
 * The API allows to provide post-processing for the diagram rendering.
 * @author ntinsalhi
 */
public interface IDiagramPostProcessor {
    /**
     * Determines whether this post-processor is applicable for the given diagram.
     * This method is called before {@code postProcess} to check whether the processor
     * should be applied to the diagram in the current context.
     *
     * @param diagramContext context containing triggering information. It also contains the diagram itself.
     * @return {@code true} if this processor should handle the diagram, {@code false} otherwise
     */
    boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext);

    /**
     * Applies custom logic to a diagram after creation or update.
     *
     * @param diagramContext context containing triggering information. It also contains the diagram itself.
     * @return The modified diagram or Optional.empty() if nothing is done.
     */
    Optional<Diagram> postProcess(IEditingContext editingContext,
                        DiagramContext diagramContext);
}
