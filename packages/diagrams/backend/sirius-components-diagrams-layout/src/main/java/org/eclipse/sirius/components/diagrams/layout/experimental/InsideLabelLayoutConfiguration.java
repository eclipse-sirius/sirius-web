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
package org.eclipse.sirius.components.diagrams.layout.experimental;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

@Immutable
public final class InsideLabelLayoutConfiguration {
    private Optional<Size> iconSize;

    private double gap;

    private Offsets padding;

    private Offsets border;

    private Offsets margin;

    private Anchor anchor;

    private LabelStyle textStyle;

    private InsideLabelLayoutConfiguration() {
        // Prevent instantiation
    }

    public Optional<Size> getIconSize() {
        return this.iconSize;
    }

    public double getGap() {
        return this.gap;
    }

    public Offsets getPadding() {
        return this.padding;
    }

    public Offsets getBorder() {
        return this.border;
    }

    public Offsets getMargin() {
        return this.margin;
    }

    public Anchor getAnchor() {
        return this.anchor;
    }

    public LabelStyle getTextStyle() {
        return this.textStyle;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'iconSize: {0}, gap: {1}, padding: {2}, border: {3}, margin: {4}, textStyle: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getIconSize(), this.getGap(), this.getPadding(), this.getBorder(), this.getMargin(), this.getTextStyle());
    }

    public static Builder newInsideLabelLayoutConfiguration() {
        return new Builder();
    }

    /**
     * Builder used to create the InsideLabelLayoutRules.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private Optional<Size> iconSize = Optional.of(new Size(16.0, 18.0));

        private double gap = 5.0;

        private Offsets padding = Offsets.of(5.0);

        private Offsets border = Offsets.empty();

        private Offsets margin = Offsets.empty();

        private Anchor anchor = Anchor.TOP_LEFT;

        private LabelStyle textStyle = LabelStyle.newLabelStyle().color("black").fontSize(16).iconURL("").build();

        public Builder iconSize(Optional<Size> iconSize) {
            this.iconSize = Objects.requireNonNull(iconSize);
            return this;
        }

        public Builder gap(double gap) {
            this.gap = Objects.requireNonNull(gap);
            return this;
        }

        public Builder padding(Offsets padding) {
            this.padding = Objects.requireNonNull(padding);
            return this;
        }

        public Builder border(Offsets border) {
            this.border = Objects.requireNonNull(border);
            return this;
        }

        public Builder margin(Offsets margin) {
            this.margin = Objects.requireNonNull(margin);
            return this;
        }

        public Builder anchor(Anchor anchor) {
            this.anchor = Objects.requireNonNull(anchor);
            return this;
        }

        public Builder textStyle(LabelStyle textStyle) {
            this.textStyle = Objects.requireNonNull(textStyle);
            return this;
        }

        public InsideLabelLayoutConfiguration build() {
            InsideLabelLayoutConfiguration insideLabelLayoutRules = new InsideLabelLayoutConfiguration();
            insideLabelLayoutRules.iconSize = Objects.requireNonNull(this.iconSize);
            insideLabelLayoutRules.gap = Objects.requireNonNull(this.gap);
            insideLabelLayoutRules.padding = Objects.requireNonNull(this.padding);
            insideLabelLayoutRules.border = Objects.requireNonNull(this.border);
            insideLabelLayoutRules.margin = Objects.requireNonNull(this.margin);
            insideLabelLayoutRules.anchor = Objects.requireNonNull(this.anchor);
            insideLabelLayoutRules.textStyle = Objects.requireNonNull(this.textStyle);
            return insideLabelLayoutRules;
        }
    }

}
