/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The list item.
 *
 * @author sbegaudeau
 */
@Immutable
public final class ListItem {

    private String id;

    private String label;

    private String kind;

    private List<String> iconURL;

    private boolean deletable;

    private Function<ClickEventKind, IStatus> clickHandler;

    private Supplier<IStatus> deleteHandler;

    private ListItem() {
        // Prevent instantiation
    }

    public static Builder newListItem(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public boolean isDeletable() {
        return this.deletable;
    }

    public Function<ClickEventKind, IStatus> getClickHandler() {
        return this.clickHandler;
    }

    public Supplier<IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}, deletable: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind, this.deletable);
    }

    /**
     * The builder used to create the list item.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private String kind;

        private List<String> iconURL;

        private boolean deletable;

        private Function<ClickEventKind, IStatus> clickHandler;

        private Supplier<IStatus> deleteHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder deletable(boolean deletable) {
            this.deletable = Objects.requireNonNull(deletable);
            return this;
        }

        public Builder clickHandler(Function<ClickEventKind, IStatus> clickHandler) {
            this.clickHandler = Objects.requireNonNull(clickHandler);
            return this;
        }

        public Builder deleteHandler(Supplier<IStatus> deleteHandler) {
            this.deleteHandler = Objects.requireNonNull(deleteHandler);
            return this;
        }

        public ListItem build() {
            ListItem listItem = new ListItem();
            listItem.id = Objects.requireNonNull(this.id);
            listItem.label = Objects.requireNonNull(this.label);
            listItem.kind = Objects.requireNonNull(this.kind);
            listItem.deletable = Objects.requireNonNull(this.deletable);
            listItem.clickHandler = Objects.requireNonNull(this.clickHandler);
            listItem.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            listItem.iconURL = Objects.requireNonNull(this.iconURL);
            return listItem;
        }
    }
}
