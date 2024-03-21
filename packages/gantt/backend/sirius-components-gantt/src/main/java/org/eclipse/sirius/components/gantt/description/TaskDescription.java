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
package org.eclipse.sirius.components.gantt.description;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The concept of the description of a gantt task.
 *
 * @author lfasani
 */
@PublicApi
public record TaskDescription(String id, Function<VariableManager, String> targetObjectIdProvider, Function<VariableManager, String> targetObjectKindProvider,
        Function<VariableManager, String> targetObjectLabelProvider, Function<VariableManager, List<Object>> semanticElementsProvider, Function<VariableManager, String> nameProvider,
        Function<VariableManager, String> descriptionProvider, Function<VariableManager, Instant> startTimeProvider, Function<VariableManager, Instant> endTimeProvider,
        Function<VariableManager, Integer> progressProvider, Function<VariableManager, Boolean> computeDatesDynamicallyProvider, Function<VariableManager, List<Object>> taskDependenciesProvider,
        List<String> reusedTaskDescriptionIds, List<TaskDescription> subTaskDescriptions) {

    public TaskDescription {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectIdProvider);
        Objects.requireNonNull(targetObjectKindProvider);
        Objects.requireNonNull(targetObjectLabelProvider);
        Objects.requireNonNull(semanticElementsProvider);
        Objects.requireNonNull(nameProvider);
        Objects.requireNonNull(descriptionProvider);
        Objects.requireNonNull(startTimeProvider);
        Objects.requireNonNull(endTimeProvider);
        Objects.requireNonNull(progressProvider);
        Objects.requireNonNull(computeDatesDynamicallyProvider);
        Objects.requireNonNull(taskDependenciesProvider);
        Objects.requireNonNull(reusedTaskDescriptionIds);
        Objects.requireNonNull(subTaskDescriptions);
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

        private Function<VariableManager, String> nameProvider;

        private Function<VariableManager, String> descriptionProvider;

        private Function<VariableManager, Instant> startTimeProvider;

        private Function<VariableManager, Instant> endTimeProvider;

        private Function<VariableManager, Integer> progressProvider;

        private Function<VariableManager, Boolean> computeDatesDynamicallyProvider;

        private Function<VariableManager, List<Object>> taskDependenciesProvider;

        private List<TaskDescription> subTaskDescriptions = List.of();

        private List<String> reusedTaskDescriptionIds = List.of();

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

        public Builder nameProvider(Function<VariableManager, String> nameProvider) {
            this.nameProvider = Objects.requireNonNull(nameProvider);
            return this;
        }

        public Builder descriptionProvider(Function<VariableManager, String> descriptionProvider) {
            this.descriptionProvider = Objects.requireNonNull(descriptionProvider);
            return this;
        }

        public Builder startTimeProvider(Function<VariableManager, Instant> startTimeProvider) {
            this.startTimeProvider = Objects.requireNonNull(startTimeProvider);
            return this;
        }

        public Builder endTimeProvider(Function<VariableManager, Instant> endTimeProvider) {
            this.endTimeProvider = Objects.requireNonNull(endTimeProvider);
            return this;
        }

        public Builder progressProvider(Function<VariableManager, Integer> progressProvider) {
            this.progressProvider = Objects.requireNonNull(progressProvider);
            return this;
        }

        public Builder computeStartEndDynamicallyProvider(Function<VariableManager, Boolean> computeDatesDynamicallyProvider) {
            this.computeDatesDynamicallyProvider = Objects.requireNonNull(computeDatesDynamicallyProvider);
            return this;
        }

        public Builder taskDependenciesProvider(Function<VariableManager, List<Object>> taskDependenciesProvider) {
            this.taskDependenciesProvider = Objects.requireNonNull(taskDependenciesProvider);
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
                    this.nameProvider, this.descriptionProvider, this.startTimeProvider, this.endTimeProvider, this.progressProvider, this.computeDatesDynamicallyProvider,
                    this.taskDependenciesProvider, this.reusedTaskDescriptionIds, this.subTaskDescriptions);
            return ganttDescription;
        }
    }
}
