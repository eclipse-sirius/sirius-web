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
package org.eclipse.sirius.components.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the Link element.
 *
 * @author ldelaigue
 */
@Immutable
public final class LinkElementProps implements IProps {
    public static final String TYPE = "Link"; //$NON-NLS-1$

    private String id;

    private String label;

    private String iconURL;

    private String url;

    private List<Element> children;

    private LinkElementProps() {
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

    public String getUrl() {
        return this.url;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newLinkElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, url: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.url);
    }

    /**
     * The builder of the Link element props.
     *
     * @author ldelaigue
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private final String id;

        private String label;

        private String iconURL;

        private String url;

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

        public Builder url(String value) {
            this.url = Objects.requireNonNull(value);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public LinkElementProps build() {
            LinkElementProps linkElementProps = new LinkElementProps();
            linkElementProps.id = Objects.requireNonNull(this.id);
            linkElementProps.label = Objects.requireNonNull(this.label);
            linkElementProps.iconURL = this.iconURL;
            linkElementProps.url = Objects.requireNonNull(this.url);
            linkElementProps.children = Objects.requireNonNull(this.children);
            return linkElementProps;
        }
    }
}
