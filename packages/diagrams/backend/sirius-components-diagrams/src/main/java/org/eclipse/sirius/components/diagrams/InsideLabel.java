/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import java.util.Set;

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

    private HeaderSeparatorDisplayMode headerSeparatorDisplayMode;

    private LabelOverflowStrategy overflowStrategy;

    private LabelTextAlign textAlign;

    private Set<String> customizedStyleProperties;

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

    public HeaderSeparatorDisplayMode getHeaderSeparatorDisplayMode() {
        return this.headerSeparatorDisplayMode;
    }

    public LabelOverflowStrategy getOverflowStrategy() {
        return this.overflowStrategy;
    }

    public LabelTextAlign getTextAlign() {
        return this.textAlign;
    }

    public Set<String> getCustomizedStyleProperties() {
        return this.customizedStyleProperties;
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

        private HeaderSeparatorDisplayMode headerSeparatorDisplayMode = HeaderSeparatorDisplayMode.NEVER;

        private LabelOverflowStrategy overflowStrategy;

        private LabelTextAlign textAlign;

        private Set<String> customizedStyleProperties;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder(InsideLabel insideLabel) {
            this.id = insideLabel.getId();
            this.text = insideLabel.getText();
            this.insideLabelLocation = insideLabel.getInsideLabelLocation();
            this.style = insideLabel.getStyle();
            this.isHeader = insideLabel.isIsHeader();
            this.headerSeparatorDisplayMode = insideLabel.getHeaderSeparatorDisplayMode();
            this.overflowStrategy = insideLabel.getOverflowStrategy();
            this.textAlign = insideLabel.getTextAlign();
            this.customizedStyleProperties = insideLabel.getCustomizedStyleProperties();
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

        public Builder headerSeparatorDisplayMode(HeaderSeparatorDisplayMode headerSeparatorDisplayMode) {
            this.headerSeparatorDisplayMode = Objects.requireNonNull(headerSeparatorDisplayMode);
            return this;
        }

        public Builder overflowStrategy(LabelOverflowStrategy overflowStrategy) {
            this.overflowStrategy = Objects.requireNonNull(overflowStrategy);
            return this;
        }

        public Builder textAlign(LabelTextAlign textAlign) {
            this.textAlign = Objects.requireNonNull(textAlign);
            return this;
        }

        public Builder customizedStyleProperties(Set<String> customizedStyleProperties) {
            this.customizedStyleProperties = Objects.requireNonNull(customizedStyleProperties);
            return this;
        }

        public InsideLabel build() {
            InsideLabel label = new InsideLabel();
            label.id = Objects.requireNonNull(this.id);
            label.text = Objects.requireNonNull(this.text);
            label.insideLabelLocation = Objects.requireNonNull(this.insideLabelLocation);
            label.style = Objects.requireNonNull(this.style);
            label.isHeader = this.isHeader;
            label.headerSeparatorDisplayMode = Objects.requireNonNull(this.headerSeparatorDisplayMode);
            label.overflowStrategy = Objects.requireNonNull(this.overflowStrategy);
            label.textAlign = Objects.requireNonNull(this.textAlign);
            label.customizedStyleProperties = Objects.requireNonNull(this.customizedStyleProperties);
            return label;
        }
    }
}
