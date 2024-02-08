/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the inside label element.
 *
 * @author gcoutable
 */
@Immutable
public final class InsideLabelElementProps implements IProps {

    public static final String TYPE = "InsideLabel";

    private String id;

    private String text;

    private InsideLabelLocation insideLabelLocation;

    private LabelStyle style;

    private boolean isHeader;

    private boolean displayHeaderSeparator;

    private InsideLabelElementProps() {
        // Prevent instantiation
    }

    public static Builder newInsideLabelElementProps(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public InsideLabelLocation getInsideLabelLocation() {
        return this.insideLabelLocation;
    }

    public LabelStyle getStyle() {
        return this.style;
    }

    public boolean isIsHeader() {
        return this.isHeader;
    }

    public boolean isDisplayHeaderSeparator() {
        return this.displayHeaderSeparator;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, text: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.text);
    }

    /**
     * The builder used to create an inside label element.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String text;

        private InsideLabelLocation insideLabelLocation;

        private LabelStyle style;

        private boolean isHeader;

        private boolean displayHeaderSeparator;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder text(String text) {
            this.text = Objects.requireNonNull(text);
            return this;
        }

        public Builder insideLabelLocation(InsideLabelLocation insideLabelLocation) {
            this.insideLabelLocation = Objects.requireNonNull(insideLabelLocation);
            return this;
        }

        public Builder style(LabelStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder isHeader(boolean isHeader) {
            this.isHeader = isHeader;
            return this;
        }

        public Builder displayHeaderSeparator(boolean displayHeaderSeparator) {
            this.displayHeaderSeparator = displayHeaderSeparator;
            return this;
        }

        public InsideLabelElementProps build() {
            InsideLabelElementProps insideLabelElementProps = new InsideLabelElementProps();
            insideLabelElementProps.id = Objects.requireNonNull(this.id);
            insideLabelElementProps.text = Objects.requireNonNull(this.text);
            insideLabelElementProps.insideLabelLocation = Objects.requireNonNull(this.insideLabelLocation);
            insideLabelElementProps.style = Objects.requireNonNull(this.style);
            insideLabelElementProps.isHeader = this.isHeader;
            insideLabelElementProps.displayHeaderSeparator = this.displayHeaderSeparator;
            return insideLabelElementProps;
        }
    }
}
