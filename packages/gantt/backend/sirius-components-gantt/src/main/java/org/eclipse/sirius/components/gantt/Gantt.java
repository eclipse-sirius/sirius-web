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

import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;

/**
 * Root concept of the Gantt representation.
 *
 * @author lfasani
 */
public record Gantt(String id, String descriptionId, String targetObjectId, String label, List<Task> tasks) implements IRepresentation, ISemanticRepresentation {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Gantt";

    public Gantt {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(descriptionId);
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

    public static Builder newGantt(Gantt gantt) {
        return new Builder(gantt);
    }

    /**
     * The builder used to create a gantt.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String targetObjectId;

        private String descriptionId;

        private String label;

        private List<Task> tasks;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Gantt gantt) {
            this.id = gantt.getId();
            this.targetObjectId = gantt.getTargetObjectId();
            this.descriptionId = gantt.getDescriptionId();
            this.label = gantt.getLabel();
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

        public Gantt build() {
            Gantt gantt = new Gantt(this.id, this.descriptionId, this.targetObjectId, this.label, this.tasks);
            return gantt;
        }
    }
}