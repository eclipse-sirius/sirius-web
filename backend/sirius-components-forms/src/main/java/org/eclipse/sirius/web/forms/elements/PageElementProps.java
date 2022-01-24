/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.representations.Element;
import org.eclipse.sirius.web.representations.IProps;

/**
 * The properties of the page element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class PageElementProps implements IProps {
    public static final String TYPE = "Page"; //$NON-NLS-1$

    private String id;

    private String label;

    private List<Element> children;

    private PageElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newPageElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the page element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public PageElementProps build() {
            PageElementProps pageElementProps = new PageElementProps();
            pageElementProps.id = Objects.requireNonNull(this.id);
            pageElementProps.label = Objects.requireNonNull(this.label);
            pageElementProps.children = Objects.requireNonNull(this.children);
            return pageElementProps;
        }
    }
}
