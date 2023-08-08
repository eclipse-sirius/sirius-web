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
package org.eclipse.sirius.components.gantt.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.gantt.TaskDetail;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The concept of the description of a gantt task.
 *
 * @author lfasani
 */
@PublicApi
public record TaskDescription(String id, Function<VariableManager, String> targetObjectIdProvider, Function<VariableManager, String> targetObjectKindProvider,
        Function<VariableManager, String> targetObjectLabelProvider, Function<VariableManager, List<Object>> semanticElementsProvider, Function<VariableManager, TaskDetail> taskDetailProvider,
        List<String> reusedTaskDescriptionIds, List<TaskDescription> subTaskDescriptions) {

    public TaskDescription {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectIdProvider);
        Objects.requireNonNull(targetObjectKindProvider);
        Objects.requireNonNull(targetObjectLabelProvider);
        Objects.requireNonNull(semanticElementsProvider);
        Objects.requireNonNull(taskDetailProvider);
        Objects.requireNonNull(reusedTaskDescriptionIds);
    }

    public static Builder newTaskDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the task description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private Function<VariableManager, String> targetObjectLabelProvider;

        private Function<VariableManager, List<Object>> semanticElementsProvider;

        private Function<VariableManager, TaskDetail> taskDetailProvider;

        private List<TaskDescription> subTaskDescriptions;

        private List<String> reusedTaskDescriptionIds;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder targetObjectKindProvider(Function<VariableManager, String> targetObjectKindProvider) {
            this.targetObjectKindProvider = Objects.requireNonNull(targetObjectKindProvider);
            return this;
        }

        public Builder targetObjectLabelProvider(Function<VariableManager, String> targetObjectLabelProvider) {
            this.targetObjectLabelProvider = Objects.requireNonNull(targetObjectLabelProvider);
            return this;
        }

        public Builder semanticElementsProvider(Function<VariableManager, List<Object>> semanticElementsProvider) {
            this.semanticElementsProvider = Objects.requireNonNull(semanticElementsProvider);
            return this;
        }

        public Builder taskDetailProvider(Function<VariableManager, TaskDetail> taskDetailProvider) {
            this.taskDetailProvider = Objects.requireNonNull(taskDetailProvider);
            return this;
        }

        public Builder reusedTaskDescriptionIds(List<String> reusedTaskDescriptionIds) {
            this.reusedTaskDescriptionIds = Objects.requireNonNull(reusedTaskDescriptionIds);
            return this;
        }

        public Builder subTaskDescriptions(List<TaskDescription> subTaskDescriptions) {
            this.subTaskDescriptions = Objects.requireNonNull(subTaskDescriptions);
            return this;
        }

        public TaskDescription build() {
            TaskDescription ganttDescription = new TaskDescription(this.id, this.targetObjectIdProvider, this.targetObjectKindProvider, this.targetObjectLabelProvider, this.semanticElementsProvider,
                    this.taskDetailProvider, this.reusedTaskDescriptionIds, this.subTaskDescriptions);
            return ganttDescription;
        }
    }
}
