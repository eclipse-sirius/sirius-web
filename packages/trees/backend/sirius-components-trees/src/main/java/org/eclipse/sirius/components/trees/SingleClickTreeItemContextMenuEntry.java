/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.trees;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Single click tree item context menu entry.
 *
 * @author Jerome Gout
 */
@Immutable
public final class SingleClickTreeItemContextMenuEntry implements ITreeItemContextMenuEntry {

    private String id;

    private Function<VariableManager, String> label;

    private Function<VariableManager, List<String>> iconURL;

    private SingleClickTreeItemContextMenuEntry() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Function<VariableManager, String> getLabel() {
        return this.label;
    }

    @Override
    public Function<VariableManager, List<String>> getIconURL() {
        return this.iconURL;
    }

    public static Builder newSingleClickTreeItemContextMenuEntry(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create the simple tree item context action.
     *
     * @author Jerome Gout
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private Function<VariableManager, String> label;

        private Function<VariableManager, List<String>> iconURL;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(Function<VariableManager, String> label) {
            this.label = label;
            return this;
        }

        public Builder iconURL(Function<VariableManager, List<String>> iconURL) {
            this.iconURL = iconURL;
            return this;
        }

        public SingleClickTreeItemContextMenuEntry build() {
            SingleClickTreeItemContextMenuEntry action = new SingleClickTreeItemContextMenuEntry();
            action.id = Objects.requireNonNull(this.id);
            action.label = Objects.requireNonNull(this.label);
            action.iconURL = Objects.requireNonNull(this.iconURL);
            return action;
        }
    }
}
