/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.gantt.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.description.GanttDescription;

/**
 * Service used to create gantt diagrams from scratch.
 *
 * @author lfasani
 */
public interface IGanttCreationService {

    /**
     * Creates a new gantt diagram using the given parameters.
     *
     * @param label
     *            The label of the diagram
     * @param targetObject
     *            The object used as the target
     * @param ganttDescription
     *            The description of the diagram
     * @param editingContext
     *            The editing context
     * @return A new gantt diagram
     */
    Gantt create(String label, Object targetObject, GanttDescription ganttDescription, IEditingContext editingContext);

    /**
     * Refresh an existing gantt.
     *
     * <p>
     * Refreshing a gantt seems to always be possible but it may not be the case. In some situation, the semantic
     * element on which the previous gantt has been created may not exist anymore and thus we can return an empty
     * optional if we are unable to refresh the gantt.
     * </p>
     *
     * @param editingContext
     *            The editing context
     * @param ganttDiagram
     *            The gantt diagram
     * @return An updated gantt if we have been able to refresh it.
     */
    Optional<Gantt> refresh(IEditingContext editingContext, Gantt ganttDiagram);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author lfasani
     */
    class NoOp implements IGanttCreationService {

        @Override
        public Gantt create(String label, Object targetObject, GanttDescription ganttDescription, IEditingContext editingContext) {
            return null;
        }

        @Override
        public Optional<Gantt> refresh(IEditingContext editingContext, Gantt ganttDiagram) {
            return Optional.empty();
        }
    }

}
