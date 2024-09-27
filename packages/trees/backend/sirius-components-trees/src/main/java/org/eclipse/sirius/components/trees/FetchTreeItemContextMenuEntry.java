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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Fetch context menu entry performed only in the front end side.
 *
 * @author Jerome Gout
 */
@Immutable
public final class FetchTreeItemContextMenuEntry implements ITreeItemContextMenuEntry {

    private String id;

    private Function<VariableManager, String> label;

    private Function<VariableManager, List<String>> iconURL;

    private Function<VariableManager, Boolean> precondition;

    private Function<VariableManager, String> urlToFetch;

    private Function<VariableManager, FetchTreeItemContextMenuEntryKind> fetchKind;

    private FetchTreeItemContextMenuEntry() {
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

    @Override
    public Function<VariableManager, Boolean> getPrecondition() {
        return this.precondition;
    }

    public Function<VariableManager, String> getUrlToFetch() {
        return this.urlToFetch;
    }

    public Function<VariableManager, FetchTreeItemContextMenuEntryKind> getFetchKind() {
        return this.fetchKind;
    }

    public static Builder newFetchTreeItemContextMenuEntry(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create the fetch tree item context action.
     *
     * @author Jerome Gout
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private Function<VariableManager, String> label;

        private Function<VariableManager, List<String>> iconURL;

        private Function<VariableManager, Boolean> precondition;

        private Function<VariableManager, String> urlToFetch;

        private Function<VariableManager, FetchTreeItemContextMenuEntryKind> fetchKind;

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

        public Builder precondition(Function<VariableManager, Boolean> precondition) {
            this.precondition = precondition;
            return this;
        }

        public Builder urlToFetch(Function<VariableManager, String> urlToFetch) {
            this.urlToFetch = urlToFetch;
            return this;
        }

        public Builder fetchKind(Function<VariableManager, FetchTreeItemContextMenuEntryKind> fetchKind) {
            this.fetchKind = fetchKind;
            return this;
        }

        public FetchTreeItemContextMenuEntry build() {
            var action = new FetchTreeItemContextMenuEntry();
            action.id = Objects.requireNonNull(this.id);
            action.label = Objects.requireNonNull(this.label);
            action.iconURL = Objects.requireNonNull(this.iconURL);
            action.precondition = Objects.requireNonNull(this.precondition);
            action.urlToFetch = Objects.requireNonNull(this.urlToFetch);
            action.fetchKind = Objects.requireNonNull(this.fetchKind);
            return action;
        }
    }
}
