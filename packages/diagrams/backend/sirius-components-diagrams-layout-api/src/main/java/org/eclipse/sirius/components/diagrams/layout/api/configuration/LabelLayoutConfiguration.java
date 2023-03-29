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
package org.eclipse.sirius.components.diagrams.layout.api.configuration;

import java.awt.Font;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.layout.api.Offsets;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Aggregates all configuration information which is relevant for the layout of a label.
 *
 * @author pcdavid
 */
@Immutable
public final class LabelLayoutConfiguration {
    private String id;

    private String text;

    private int fontSize;

    private FontStyle fontStyle;

    private Optional<Size> iconSize;

    private double gap;

    private Offsets border;

    private Offsets padding;

    private Offsets margin;

    private LabelLayoutConfiguration() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    /**
     * Text actual text of the label.
     *
     * @return the text of the label. May be empty, but never <code>null</code>.
     */
    public String getText() {
        return this.text;
    }

    /**
     * The {@link Font#getSize() font size} to use to for the label's text.
     *
     * @return the font size.
     */
    public int getFontSize() {
        return this.fontSize;
    }

    /**
     * The style modifiers for the label's text which can impact its layout.
     *
     * @return the font style.
     */
    public FontStyle getFontStyle() {
        return this.fontStyle;
    }

    /**
     * The size of the icon part of the label, if present.
     *
     * @return the size of the (optional) icon.
     */
    public Optional<Size> getIconSize() {
        return this.iconSize;
    }

    /**
     * The horizontal gap between the icon (if present) and the text of the label. This is ignored if the label does not
     * have an icon.
     *
     * @return the gap, in pixels.
     */
    public double getGap() {
        return this.gap;
    }

    public Offsets getBorder() {
        return this.border;
    }

    /**
     * The amount of (internal) padding around the icon & text of the label.
     *
     * @return the padding.
     */
    public Offsets getPadding() {
        return this.padding;
    }

    /**
     * The amount of (external) margin around the label.
     *
     * @return the margin.
     */
    public Offsets getMargin() {
        return this.margin;
    }


    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, text: {2} '}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.getText());
    }

    public static Builder newLabelLayoutConfiguration(String id) {
        return new Builder(id);
    }

    /**
     * Builder used to create the LabelLayoutConfiguration.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String text;

        private int fontSize = 16;

        private FontStyle fontStyle = FontStyle.DEFAULT;

        private Optional<Size> iconSize = Optional.empty();

        private double gap;

        private Offsets border = Offsets.empty();

        private Offsets padding = Offsets.of(5.0);

        private Offsets margin = Offsets.empty();

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder text(String text) {
            this.text = Objects.requireNonNull(text);
            return this;
        }

        public Builder fontSize(int fontSize) {
            this.fontSize = Objects.requireNonNull(fontSize);
            return this;
        }

        public Builder fontStyle(FontStyle fontStyle) {
            this.fontStyle = fontStyle;
            return this;
        }

        public Builder iconSize(Optional<Size> iconSize) {
            this.iconSize = Objects.requireNonNull(iconSize);
            return this;
        }

        public Builder gap(double gap) {
            this.gap = gap;
            return this;
        }

        public Builder border(Offsets border) {
            this.border = Objects.requireNonNull(border);
            return this;
        }

        public Builder padding(Offsets padding) {
            this.padding = Objects.requireNonNull(padding);
            return this;
        }

        public Builder margin(Offsets margin) {
            this.margin = Objects.requireNonNull(margin);
            return this;
        }

        public LabelLayoutConfiguration build() {
            LabelLayoutConfiguration labelLayoutConfiguration = new LabelLayoutConfiguration();
            labelLayoutConfiguration.id = Objects.requireNonNull(this.id);
            labelLayoutConfiguration.text = Objects.requireNonNull(this.text);
            labelLayoutConfiguration.fontSize = this.fontSize;
            labelLayoutConfiguration.fontStyle = Objects.requireNonNull(this.fontStyle);
            labelLayoutConfiguration.iconSize = Objects.requireNonNull(this.iconSize);
            labelLayoutConfiguration.gap = this.gap;
            labelLayoutConfiguration.border = Objects.requireNonNull(this.border);
            labelLayoutConfiguration.padding = Objects.requireNonNull(this.padding);
            labelLayoutConfiguration.margin = Objects.requireNonNull(this.margin);
            return labelLayoutConfiguration;
        }
    }

}
