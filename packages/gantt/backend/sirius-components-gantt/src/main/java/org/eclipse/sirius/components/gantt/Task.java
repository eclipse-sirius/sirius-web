/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.gantt;

import java.util.List;
import java.util.Objects;

/**
 * Concept of task.
 *
 * @author lfasani
 */
public record Task(String id, String descriptionId, String targetObjectId, String targetObjectKind, String targetObjectLabel, TaskDetail detail, List<String> taskDependencyIds, List<Task> subTasks) {

    public Task {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(targetObjectLabel);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(detail);
        Objects.requireNonNull(taskDependencyIds);
        Objects.requireNonNull(subTasks);
    }
}
