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
package org.eclipse.sirius.components.gantt.tests.navigation;

import java.util.Objects;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.Task;

/**
 * Entry point of the navigation in a gantt representation.
 *
 * @author lfasani
 */
public class GanttNavigator {

    private final Gantt gantt;

    public GanttNavigator(Gantt gantt) {
        this.gantt = Objects.requireNonNull(gantt);
    }

    public Task findTaskByName(String name) {
        return this.gantt.tasks().stream()
                .map(task -> this.findTaskByName(task, name))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No task found with the given name \"" + name + "\""));
    }

    private Task findTaskByName(Task task, String name) {
        Task foundTask = null;
        if (task.detail().name().equals(name)) {
            foundTask =  task;
        } else {
            for (Task subTask : task.subTasks()) {
                foundTask = this.findTaskByName(subTask, name);
                if (foundTask != null) {
                    break;
                }
            }
        }

        return foundTask;
    }
}
