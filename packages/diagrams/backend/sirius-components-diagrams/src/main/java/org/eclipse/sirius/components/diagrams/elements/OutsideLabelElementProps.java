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
package org.eclipse.sirius.components.diagrams.elements;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.OutsideLabelLocation;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the outside label element.
 *
 * @author frouene
 */
@Immutable
public final class OutsideLabelElementProps implements IProps {

    public static final String TYPE = "OutsideLabel";

    private String id;

    private String text;

    private OutsideLabelLocation outsideLabelLocation;

    private LabelStyle style;

    private OutsideLabelElementProps() {
        // Prevent instantiation
    }

    public static Builder newOutsideLabelElementProps(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public OutsideLabelLocation getOutsideLabelLocation() {
        return this.outsideLabelLocation;
    }

    public LabelStyle getStyle() {
        return this.style;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, text: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.text);
    }

    /**
     * The builder used to create an outside label element.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String text;

        private OutsideLabelLocation outsideLabelLocation;

        private LabelStyle style;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder text(String text) {
            this.text = Objects.requireNonNull(text);
            return this;
        }

        public Builder outsideLabelLocation(OutsideLabelLocation outsideLabelLocation) {
            this.outsideLabelLocation = Objects.requireNonNull(outsideLabelLocation);
            return this;
        }

        public Builder style(LabelStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public OutsideLabelElementProps build() {
            OutsideLabelElementProps outsideLabelElementProps = new OutsideLabelElementProps();
            outsideLabelElementProps.id = Objects.requireNonNull(this.id);
            outsideLabelElementProps.text = Objects.requireNonNull(this.text);
            outsideLabelElementProps.outsideLabelLocation = Objects.requireNonNull(this.outsideLabelLocation);
            outsideLabelElementProps.style = Objects.requireNonNull(this.style);
            return outsideLabelElementProps;
        }
    }
}
