/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api.experimental;

import java.util.Objects;
import java.util.Optional;

/**
 * Aggregates all configuration information which is relevant for the layout of a label.
 *
 * @author pcdavid
 */
public record LabelLayoutConfiguration(
        String id,
        String text,
        int fontSize,
        FontStyle fontStyle,
        Optional<IconLayoutConfiguration> optionalIconLayoutConfiguration,
        Offsets border,
        Offsets padding,
        Offsets margin) {

    public LabelLayoutConfiguration {
        Objects.requireNonNull(id);
        Objects.requireNonNull(text);
        Objects.requireNonNull(fontStyle);
        Objects.requireNonNull(optionalIconLayoutConfiguration);
        Objects.requireNonNull(border);
        Objects.requireNonNull(padding);
        Objects.requireNonNull(margin);
    }

    public static Builder newLabelLayoutConfiguration(String id) {
        return new Builder(id);
    }

    /**
     * Builder used to create the LabelLayoutConfiguration.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {
        private String id;

        private String text;

        private int fontSize;

        private FontStyle fontStyle;

        private IconLayoutConfiguration iconLayoutConfiguration;

        private Offsets border;

        private Offsets padding;

        private Offsets margin;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder text(String text) {
            this.text = Objects.requireNonNull(text);
            return this;
        }

        public Builder fontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder fontStyle(FontStyle fontStyle) {
            this.fontStyle = fontStyle;
            return this;
        }

        public Builder iconLayoutConfiguration(IconLayoutConfiguration iconLayoutConfiguration) {
            this.iconLayoutConfiguration = Objects.requireNonNull(iconLayoutConfiguration);
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
            return new LabelLayoutConfiguration(
                    this.id,
                    this.text,
                    this.fontSize,
                    this.fontStyle,
                    Optional.ofNullable(this.iconLayoutConfiguration),
                    this.border,
                    this.padding,
                    this.margin
            );
        }
    }

}
