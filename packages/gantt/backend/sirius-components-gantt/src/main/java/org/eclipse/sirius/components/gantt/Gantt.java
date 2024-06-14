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

import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Root concept of the Gantt representation.
 *
 * @author lfasani
 */
public record Gantt(String id, String descriptionId, String targetObjectId, String label, List<Task> tasks, List<GanttColumn> columns, GanttDateRounding dateRounding) implements IRepresentation {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Gantt";

    public Gantt {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(descriptionId);
    }

    public static Builder newGantt(Gantt gantt) {
        return new Builder(gantt);
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getKind() {
        return KIND;
    }

    /**
     * The builder used to create a gantt.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private final String id;

        private String targetObjectId;

        private String descriptionId;

        private String label;

        private List<Task> tasks;

        private List<GanttColumn> columns;

        private GanttDateRounding dateRounding;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Gantt gantt) {
            this.id = gantt.getId();
            this.targetObjectId = gantt.getTargetObjectId();
            this.descriptionId = gantt.getDescriptionId();
            this.label = gantt.getLabel();
            this.dateRounding = gantt.dateRounding();
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder tasks(List<Task> tasks) {
            this.tasks = Objects.requireNonNull(tasks);
            return this;
        }

        public Builder columns(List<GanttColumn> columns) {
            this.columns = Objects.requireNonNull(columns);
            return this;
        }

        public Builder dateRounding(GanttDateRounding dateRounding) {
            this.dateRounding = Objects.requireNonNull(dateRounding);
            return this;
        }

        public Gantt build() {
            Gantt gantt = new Gantt(this.id, this.descriptionId, this.targetObjectId, this.label, this.tasks, this.columns, this.dateRounding);
            return gantt;
        }
    }
}
