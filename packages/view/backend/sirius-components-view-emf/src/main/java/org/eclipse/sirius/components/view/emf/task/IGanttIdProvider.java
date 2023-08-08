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
package org.eclipse.sirius.components.view.emf.task;

import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.TaskDescription;

/**
 * Interface to provide ids for GanttDescription.
 *
 * @author lfasani
 */
public interface IGanttIdProvider extends IRepresentationDescriptionIdProvider<GanttDescription> {

    String GANTT_DESCRIPTION_KIND = PREFIX + "?kind=ganttDescription";

    String TASK_DESCRIPTION_KIND = PREFIX + "?kind=taskDescription";

    @Override
    String getId(GanttDescription ganttDescription);

    String getId(TaskDescription taskDescription);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author lfasani
     */
    class NoOp implements IGanttIdProvider {

        @Override
        public String getId(GanttDescription ganttDescription) {
            return "";
        }

        @Override
        public String getId(TaskDescription taskDescription) {
            return "";
        }
    }
}
