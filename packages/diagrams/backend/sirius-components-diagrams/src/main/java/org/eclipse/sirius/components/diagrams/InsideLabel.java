/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

    private String type;

    private String text;

    private InsideLabelLocation insideLabelLocation;

    private Position position;

    private Size size;

    private Position alignment;

    private LabelStyle style;

    private boolean isHeader;

    private InsideLabel() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }

    public InsideLabelLocation getInsideLabelLocation() {
        return this.insideLabelLocation;
    }

    public Position getPosition() {
        return this.position;
    }

    public Size getSize() {
        return this.size;
    }

    public Position getAlignment() {
        return this.alignment;
    }

    public LabelStyle getStyle() {
        return this.style;
    }

    public boolean isIsHeader() {
        return this.isHeader;
    }

    public static Builder newLabel(String id) {
        return new Builder(id);
    }

    public static Builder newInsideLabel(InsideLabel insideLabel) {
        return new Builder(insideLabel);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}, text: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.type, this.text);
    }

    /**
     * The builder used to create a label.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String type;

        private String text;

        private InsideLabelLocation insideLabelLocation;

        private Position position;

        private Size size;

        private Position alignment;

        private LabelStyle style;

        private boolean isHeader;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder(InsideLabel insideLabel) {
            this.id = insideLabel.getId();
            this.type = insideLabel.getType();
            this.text = insideLabel.getText();
            this.insideLabelLocation = insideLabel.getInsideLabelLocation();
            this.position = insideLabel.getPosition();
            this.size = insideLabel.getSize();
            this.alignment = insideLabel.getAlignment();
            this.style = insideLabel.getStyle();
            this.isHeader = insideLabel.isIsHeader();
        }

        public Builder type(String type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder text(String text) {
            this.text = Objects.requireNonNull(text);
            return this;
        }

        public Builder insideLabelLocation(InsideLabelLocation insideLabelLocation) {
            this.insideLabelLocation = Objects.requireNonNull(insideLabelLocation);
            return this;
        }

        public Builder position(Position position) {
            this.position = Objects.requireNonNull(position);
            return this;
        }

        public Builder size(Size size) {
            this.size = Objects.requireNonNull(size);
            return this;
        }

        public Builder alignment(Position aligment) {
            this.alignment = Objects.requireNonNull(aligment);
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

        public InsideLabel build() {
            InsideLabel label = new InsideLabel();
            label.id = Objects.requireNonNull(this.id);
            label.type = Objects.requireNonNull(this.type);
            label.text = Objects.requireNonNull(this.text);
            label.insideLabelLocation = Objects.requireNonNull(this.insideLabelLocation);
            label.position = Objects.requireNonNull(this.position);
            label.size = Objects.requireNonNull(this.size);
            label.alignment = Objects.requireNonNull(this.alignment);
            label.style = Objects.requireNonNull(this.style);
            label.isHeader = this.isHeader;
            return label;
        }
    }
}
