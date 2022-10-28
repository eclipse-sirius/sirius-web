/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
 * "Virtual" container used only during the rendering process to distinguish toolbar actions from the actual contents of
 * a Group.
 *
 * @author pcdavid
 */
@Immutable
public final class GroupContents {
    public static final String TYPE = "GroupContents"; //$NON-NLS-1$

    private List<AbstractWidget> widgets;

    private GroupContents() {
        // Prevent instantiation
    }

    public List<AbstractWidget> getWidgets() {
        return this.widgets;
    }

    public static Builder newGroupContents() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'widgetsCount: {1},'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.widgets.size());
    }

    /**
     * The builder used to create a new GroupContents.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private List<AbstractWidget> widgets = List.of();

        private Builder() {
        }

        public Builder widgets(List<AbstractWidget> widgets) {
            this.widgets = Objects.requireNonNull(widgets);
            return this;
        }

        public GroupContents build() {
            GroupContents groupContents = new GroupContents();
            groupContents.widgets = Objects.requireNonNull(this.widgets);
            return groupContents;
        }
    }
}
