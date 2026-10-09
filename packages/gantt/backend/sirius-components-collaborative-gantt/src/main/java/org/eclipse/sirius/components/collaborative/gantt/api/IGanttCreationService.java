/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.gantt.GanttContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.description.GanttDescription;

/**
 * Service used to create gantt diagrams from scratch.
 *
 * @author lfasani
 */
public interface IGanttCreationService {
    Gantt create(IEditingContext editingContext, GanttDescription ganttDescription, Object targetObject, GanttContext ganttContext);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author lfasani
     */
    class NoOp implements IGanttCreationService {

        @Override
        public Gantt create(IEditingContext editingContext, GanttDescription ganttDescription, Object targetObject, GanttContext ganttContext) {
            return null;
        }
    }
}
