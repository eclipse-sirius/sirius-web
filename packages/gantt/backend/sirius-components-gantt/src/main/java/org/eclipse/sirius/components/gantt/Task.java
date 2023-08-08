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
package org.eclipse.sirius.components.gantt;

import java.util.List;
import java.util.Objects;

/**
 * Concept of task.
 *
 * @author lfasani
 */
public record Task(String id, String descriptionId, String targetObjectId, String targetObjectKind, String targetObjectLabel, TaskDetail detail, List<Task> subTasks) {

    public Task {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(targetObjectLabel);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(detail);
        Objects.requireNonNull(subTasks);
    }

    /**
     * The builder used to create the task.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String targetObjectId;

        private String targetObjectKind;

        private String targetObjectLabel;

        private String descriptionId;

        private TaskDetail taskDetail;

        private List<Task> subTasks;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder() {
        }

        public Builder label(String id) {
            this.id = Objects.requireNonNull(id);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
            return this;
        }

        public Builder targetObjectLabel(String targetObjectLabel) {
            this.targetObjectLabel = Objects.requireNonNull(targetObjectLabel);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder taskDetail(TaskDetail taskDetail) {
            this.taskDetail = Objects.requireNonNull(taskDetail);
            return this;
        }

        public Builder subTasks(List<Task> subTasks) {
            this.subTasks = Objects.requireNonNull(subTasks);
            return this;
        }

        public Task build() {
            Task task = new Task(this.id, this.targetObjectId, this.targetObjectKind, this.targetObjectLabel, this.descriptionId, this.taskDetail, this.subTasks);
            return task;
        }
    }
}
