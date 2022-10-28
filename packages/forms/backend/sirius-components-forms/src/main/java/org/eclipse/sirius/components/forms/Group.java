/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * A part of a page used to contain the widgets.
 *
 * @author sbegaudeau
 */
@Immutable
public final class Group {
    private String id;

    private String label;

    private GroupDisplayMode displayMode;

    private List<Button> buttons;

    private List<AbstractWidget> widgets;

    private Group() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<Button> getButtons() {
        return this.buttons;
    }

    public List<AbstractWidget> getWidgets() {
        return this.widgets;
    }

    public GroupDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    public static Builder newGroup(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, displayMode: {3}, buttonsCount: {4}, widgetCount: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.displayMode, this.buttons.size(), this.widgets.size());
    }

    /**
     * The builder used to create a new group.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private GroupDisplayMode displayMode = GroupDisplayMode.LIST;

        private List<Button> buttons = List.of();

        private List<AbstractWidget> widgets;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder displayMode(GroupDisplayMode displayMode) {
            this.displayMode = Objects.requireNonNull(displayMode);
            return this;
        }

        public Builder buttons(List<Button> buttons) {
            this.buttons = Objects.requireNonNull(buttons);
            return this;
        }

        public Builder widgets(List<AbstractWidget> widgets) {
            this.widgets = Objects.requireNonNull(widgets);
            return this;
        }

        public Group build() {
            Group group = new Group();
            group.id = Objects.requireNonNull(this.id);
            group.label = Objects.requireNonNull(this.label);
            group.displayMode = Objects.requireNonNull(this.displayMode);
            group.widgets = Objects.requireNonNull(this.widgets);
            group.buttons = Objects.requireNonNull(this.buttons);
            return group;
        }
    }
}
