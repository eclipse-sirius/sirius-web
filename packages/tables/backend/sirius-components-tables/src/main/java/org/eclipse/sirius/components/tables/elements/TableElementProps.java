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
package org.eclipse.sirius.components.tables.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the table element.
 *
 * @author arichard
 */
@Immutable
public final class TableElementProps implements IProps {

    public static final String TYPE = "Table"; //$NON-NLS-1$

    private String id;

    private String targetObjectId;

    private String descriptionId;

    private String label;

    private List<Element> children;

    private TableElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newTableElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
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

        private String targetObjectId;

        private String descriptionId;

        private String label;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
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

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public TableElementProps build() {
            TableElementProps tableElementProps = new TableElementProps();
            tableElementProps.id = Objects.requireNonNull(this.id);
            tableElementProps.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            tableElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            tableElementProps.label = Objects.requireNonNull(this.label);
            tableElementProps.children = Objects.requireNonNull(this.children);
            return tableElementProps;
        }
    }
}
