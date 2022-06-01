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
 * The properties of the ChartWidget element.
 *
 * @author fbarbin
 */
@Immutable
public final class ChartWidgetElementProps implements IProps {
    public static final String TYPE = "ChartWidget"; //$NON-NLS-1$

    private String id;

    private String label;

    private List<Element> children;

    private ChartWidgetElementProps() {
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

    public static Builder newChartWidgetElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
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

        public ChartWidgetElementProps build() {
            ChartWidgetElementProps linkElementProps = new ChartWidgetElementProps();
            linkElementProps.id = Objects.requireNonNull(this.id);
            linkElementProps.label = Objects.requireNonNull(this.label);
            linkElementProps.children = Objects.requireNonNull(this.children);
            return linkElementProps;
        }
    }
}
