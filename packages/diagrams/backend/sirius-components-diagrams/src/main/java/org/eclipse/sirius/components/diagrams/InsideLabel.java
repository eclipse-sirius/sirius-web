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
package org.eclipse.sirius.components.diagrams;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * An inside label.
 *
 * @author gcoutable
 */
@Immutable
public final class InsideLabel {

    private String id;

    private String text;

    private InsideLabelLocation insideLabelLocation;

    private LabelStyle style;

    private boolean isHeader;

    private boolean displayHeaderSeparator;

    private InsideLabel() {
        // Prevent instantiation
    }

    public static Builder newLabel(String id) {
        return new Builder(id);
    }

    public static Builder newInsideLabel(InsideLabel insideLabel) {
        return new Builder(insideLabel);
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
     * The builder used to create a label.
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

        public Builder(InsideLabel insideLabel) {
            this.id = insideLabel.getId();
            this.text = insideLabel.getText();
            this.insideLabelLocation = insideLabel.getInsideLabelLocation();
            this.style = insideLabel.getStyle();
            this.isHeader = insideLabel.isIsHeader();
            this.displayHeaderSeparator = insideLabel.isDisplayHeaderSeparator();

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

        public InsideLabel build() {
            InsideLabel label = new InsideLabel();
            label.id = Objects.requireNonNull(this.id);
            label.text = Objects.requireNonNull(this.text);
            label.insideLabelLocation = Objects.requireNonNull(this.insideLabelLocation);
            label.style = Objects.requireNonNull(this.style);
            label.isHeader = this.isHeader;
            label.displayHeaderSeparator = this.displayHeaderSeparator;
            return label;
        }
    }
}
