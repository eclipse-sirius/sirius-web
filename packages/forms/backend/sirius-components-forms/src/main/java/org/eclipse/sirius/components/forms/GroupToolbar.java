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
public final class GroupToolbar {
    public static final String TYPE = "GroupToolbar"; //$NON-NLS-1$

    private List<Button> toolbarActions;

    private GroupToolbar() {
        // Prevent instantiation
    }

    public List<Button> getToolbarActions() {
        return this.toolbarActions;
    }

    public static Builder newGroupToolbar() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'toolbarActionsCount: {1},'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.toolbarActions.size());
    }

    /**
     * The builder used to create a new GroupToolbar.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private List<Button> toolbarActions = List.of();

        private Builder() {
        }

        public Builder toolbarActions(List<Button> toolbarActions) {
            this.toolbarActions = Objects.requireNonNull(toolbarActions);
            return this;
        }

        public GroupToolbar build() {
            GroupToolbar groupToolbar = new GroupToolbar();
            groupToolbar.toolbarActions = Objects.requireNonNull(this.toolbarActions);
            return groupToolbar;
        }
    }
}
