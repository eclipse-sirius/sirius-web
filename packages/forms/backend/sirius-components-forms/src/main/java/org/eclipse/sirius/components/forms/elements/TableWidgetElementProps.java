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
package org.eclipse.sirius.components.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the table element.
 *
 * @author lfasani
 */
@Immutable
public final class TableWidgetElementProps implements IProps {

    public static final String TYPE = "TableWidget";

    private String id;

    private String label;

    private List<String> iconURL;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private List<Element> children;

    private TableWidgetElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newTableWidgetElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass(), this.id);
    }

    /**
     * The builder of the table element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private List<String> iconURL;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public TableWidgetElementProps.Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public TableWidgetElementProps.Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public TableWidgetElementProps.Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public TableWidgetElementProps.Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public TableWidgetElementProps.Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public TableWidgetElementProps build() {
            TableWidgetElementProps tableWidgetElementProps = new TableWidgetElementProps();
            tableWidgetElementProps.id = Objects.requireNonNull(this.id);
            tableWidgetElementProps.label = Objects.requireNonNull(this.label);
            tableWidgetElementProps.iconURL = this.iconURL;
            tableWidgetElementProps.readOnly = this.readOnly;
            tableWidgetElementProps.helpTextProvider = this.helpTextProvider;
            tableWidgetElementProps.children = Objects.requireNonNull(this.children);
            return tableWidgetElementProps;
        }
    }
}
