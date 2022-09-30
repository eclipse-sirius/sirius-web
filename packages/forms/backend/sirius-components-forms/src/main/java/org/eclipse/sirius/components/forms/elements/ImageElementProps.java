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
 * The properties of the Image element.
 *
 * @author pcdavid
 */
@Immutable
public final class ImageElementProps implements IProps {
    public static final String TYPE = "Image"; //$NON-NLS-1$

    private String id;

    private String label;

    private String iconURL;

    private String url;

    private String maxWidth;

    private List<Element> children;

    private ImageElementProps() {
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

    public String getMaxWidth() {
        return this.maxWidth;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newImageElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, url: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.url);
    }

    /**
     * The builder of the Image element props.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private final String id;

        private String label;

        private String iconURL;

        private String url;

        private String maxWidth;

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

        public Builder maxWidth(String maxWidth) {
            this.maxWidth = Objects.requireNonNull(maxWidth);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public ImageElementProps build() {
            ImageElementProps imageElementProps = new ImageElementProps();
            imageElementProps.id = Objects.requireNonNull(this.id);
            imageElementProps.label = Objects.requireNonNull(this.label);
            imageElementProps.iconURL = this.iconURL;
            imageElementProps.url = Objects.requireNonNull(this.url);
            imageElementProps.maxWidth = this.maxWidth;
            imageElementProps.children = Objects.requireNonNull(this.children);
            return imageElementProps;
        }
    }
}
