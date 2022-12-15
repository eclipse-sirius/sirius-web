/*******************************************************************************
 * Copyright (c) 2019, 2020, 2022 Obeo.
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
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the group element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class GroupElementProps implements IProps {
    public static final String TYPE = "Group";

    private String id;

    private String label;

    private GroupDisplayMode displayMode;

    private List<Element> children;

    private GroupElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public GroupDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newGroupElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the group element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private GroupDisplayMode displayMode = GroupDisplayMode.LIST;

        private List<Element> children;

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

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public GroupElementProps build() {
            GroupElementProps groupElementProps = new GroupElementProps();
            groupElementProps.id = Objects.requireNonNull(this.id);
            groupElementProps.label = Objects.requireNonNull(this.label);
            groupElementProps.displayMode = Objects.requireNonNull(this.displayMode);
            groupElementProps.children = Objects.requireNonNull(this.children);
            return groupElementProps;
        }
    }
}
