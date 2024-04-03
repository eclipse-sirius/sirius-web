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
import java.util.Optional;

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
                .flatMap(task -> this.findTaskByName(task, name).stream())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No task found with the given name \"" + name + "\""));
    }

    private Optional<Task> findTaskByName(Task task, String name) {
        Optional<Task> optionalTask = Optional.empty();
        if (task.detail().name().equals(name)) {
            optionalTask = Optional.of(task);
        } else {
            optionalTask = task.subTasks().stream()
                    .flatMap(subTask -> this.findTaskByName(subTask, name).stream())
                    .findFirst();
        }

        return optionalTask;
    }

    public boolean existTaskByName(String name) {
        return this.gantt.tasks().stream()
                .flatMap(task -> this.findTaskByName(task, name).stream())
                .findFirst()
                .isPresent();
    }
}
