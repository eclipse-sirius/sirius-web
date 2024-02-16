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
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a gantt representation.
 *
 * @author lfasani
 */
@PublicApi
public record GanttDescription(String id, String label, Function<VariableManager, String> idProvider, Function<VariableManager, String> labelProvider,
        Function<VariableManager, String> targetObjectIdProvider, Predicate<VariableManager> canCreatePredicate, List<TaskDescription> taskDescriptions, Consumer<VariableManager> createTaskProvider,
        Consumer<VariableManager> editTaskProvider, Consumer<VariableManager> deleteTaskProvider, Consumer<VariableManager> dropTaskProvider) implements IRepresentationDescription {

    public static final String LABEL = "label";

    public static final String NEW_NAME = "newName";

    public static final String NEW_DESCRIPTION = "newDescription";

    public static final String NEW_START_TIME = "newStartTime";

    public static final String NEW_END_TIME = "newEndTime";

    public static final String NEW_PROGRESS = "newProgress";

    public static final String SOURCE_OBJECT = "source";

    public static final String TARGET_OBJECT = "target";

    public static final String SOURCE_TASK = "sourceTask";

    public static final String TARGET_TASK_OR_GANTT = "targetTaskOrGantt";

    public static final String TARGET_DROP_INDEX = "indexInTarget";

    public GanttDescription {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(idProvider);
        Objects.requireNonNull(labelProvider);
        Objects.requireNonNull(canCreatePredicate);
        Objects.requireNonNull(targetObjectIdProvider);
        Objects.requireNonNull(taskDescriptions);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public static Builder newGanttDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * Builder used to create the gantt description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private Consumer<VariableManager> deleteTaskProvider;

        private Consumer<VariableManager> editTaskProvider;

        private Consumer<VariableManager> createTaskProvider;

        private Consumer<VariableManager> dropTaskProvider;

        private List<TaskDescription> taskDescriptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder deleteTaskProvider(Consumer<VariableManager> deleteTaskProvider) {
            this.deleteTaskProvider = deleteTaskProvider;
            return this;
        }

        public Builder dropTaskProvider(Consumer<VariableManager> dropTaskProvider) {
            this.dropTaskProvider = dropTaskProvider;
            return this;
        }

        public Builder editTaskProvider(Consumer<VariableManager> editTaskProvider) {
            this.editTaskProvider = editTaskProvider;
            return this;
        }

        public Builder createTaskProvider(Consumer<VariableManager> createTaskProvider) {
            this.createTaskProvider = createTaskProvider;
            return this;
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
            return this;
        }

        public Builder taskDescriptions(List<TaskDescription> taskDescriptions) {
            this.taskDescriptions = Objects.requireNonNull(taskDescriptions);
            return this;
        }

        public GanttDescription build() {
            GanttDescription ganttDescription = new GanttDescription(this.id, this.label, this.idProvider, this.labelProvider, this.targetObjectIdProvider, this.canCreatePredicate,
                    this.taskDescriptions, this.createTaskProvider, this.editTaskProvider, this.deleteTaskProvider, this.dropTaskProvider);
            return ganttDescription;
        }
    }
}
