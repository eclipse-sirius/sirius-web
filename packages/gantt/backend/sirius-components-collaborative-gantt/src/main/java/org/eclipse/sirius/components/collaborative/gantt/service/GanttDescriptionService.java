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
package org.eclipse.sirius.components.collaborative.gantt.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.gantt.description.TaskDescription;
import org.springframework.stereotype.Service;

/**
 * service used to search in GanttDescription.
 *
 * @author lfasani
 */
@Service
public class GanttDescriptionService {

    public Optional<TaskDescription> findTaskDescriptionById(GanttDescription ganttDescription, String taskDescriptionId) {
        return this.findTaskDescription(taskDesc -> Objects.equals(taskDesc.id(), taskDescriptionId), ganttDescription.taskDescriptions());
    }

    private Optional<TaskDescription> findTaskDescription(Predicate<TaskDescription> condition, List<TaskDescription> candidates) {
        Optional<TaskDescription> result = Optional.empty();
        for (TaskDescription taskDesc : candidates) {
            if (condition.test(taskDesc)) {
                result = Optional.of(taskDesc);
            } else {
                result = this.findTaskDescription(condition, taskDesc.subTaskDescriptions());
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }
}
