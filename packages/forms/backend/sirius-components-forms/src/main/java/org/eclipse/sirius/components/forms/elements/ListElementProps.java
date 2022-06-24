/*******************************************************************************
 * Copyright (c) 2019, 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.ListItem;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the list element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class ListElementProps implements IProps {
    public static final String TYPE = "List"; //$NON-NLS-1$

    private String id;

    private String label;

    private String iconURL;

    private ListStyle style;

    private List<ListItem> items;

    private List<Element> children;

    private ListElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public ListStyle getStyle() {
        return this.style;
    }

    public List<ListItem> getItems() {
        return this.items;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newListElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, items: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.items);
    }

    /**
     * The builder of the list element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private String iconURL;

        private ListStyle style;

        private List<ListItem> items;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder style(ListStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder items(List<ListItem> items) {
            this.items = Objects.requireNonNull(items);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public ListElementProps build() {
            ListElementProps listElementProps = new ListElementProps();
            listElementProps.id = Objects.requireNonNull(this.id);
            listElementProps.label = Objects.requireNonNull(this.label);
            listElementProps.iconURL = this.iconURL;
            listElementProps.style = this.style; // Optional on purpose
            listElementProps.items = Objects.requireNonNull(this.items);
            listElementProps.children = Objects.requireNonNull(this.children);
            return listElementProps;
        }
    }
}
