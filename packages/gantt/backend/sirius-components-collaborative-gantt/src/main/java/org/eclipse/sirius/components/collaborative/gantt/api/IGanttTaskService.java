/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.gantt.dto.input.CreateGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DeleteGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DropGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.EditGanttTaskInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.gantt.Gantt;

/**
 * Service used to manage gantt task.
 *
 * @author lfasani
 */
public interface IGanttTaskService {

    /**
     * Creates a new Gantt task using the given parameters.
     */
    IPayload createTask(CreateGanttTaskInput createGanttTaskInput, IEditingContext editingContext, Gantt gantt);

    /**
     * Delete a Gantt task.
     */
    IPayload deleteTask(DeleteGanttTaskInput deleteGanttTaskInput, IEditingContext editingContext, Gantt gantt);

    /**
     * Edit an existing task.
     */
    IPayload editTask(EditGanttTaskInput editGanttTaskInput, IEditingContext editingContext, Gantt gantt);



    /**
     * Drop a task on another one.
     */
    IPayload dropTask(DropGanttTaskInput input, IEditingContext editingContext, Gantt gantt);


    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author lfasani
     */
    class NoOp implements IGanttTaskService {

        @Override
        public IPayload createTask(CreateGanttTaskInput createGanttTaskInput, IEditingContext editingContext, Gantt gantt) {
            return null;
        }

        @Override
        public IPayload editTask(EditGanttTaskInput editGanttTaskInput, IEditingContext editingContext, Gantt gantt) {
            return null;
        }

        @Override
        public IPayload deleteTask(DeleteGanttTaskInput deleteGanttTaskInput, IEditingContext editingContext, Gantt gantt) {
            return null;
        }

        @Override
        public IPayload dropTask(DropGanttTaskInput input, IEditingContext editingContext, Gantt gantt) {
            return null;
        }
    }
}
