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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The style of a DateTime.
 *
 * @author lfasani
 */
@Immutable
public final class DateTimeStyle {

    private String backgroundColor;

    private String foregroundColor;

    private boolean italic;

    private boolean bold;

    private DateTimeStyle() {
        // Prevent instantiation
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public String getForegroundColor() {
        return this.foregroundColor;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public boolean isBold() {
        return this.bold;
    }

    public static Builder newDateTimeStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'backgroundColor: {1}, foregroundColor: {2}, italic: {3}, bold: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.backgroundColor, this.foregroundColor, this.italic, this.bold);
    }

    /**
     * Builder used to create the dateTime style.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String backgroundColor;

        private String foregroundColor;

        private boolean italic;

        private boolean bold;

        private Builder() {
        }

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = Objects.requireNonNull(backgroundColor);
            return this;
        }

        public Builder foregroundColor(String foregroundColor) {
            this.foregroundColor = Objects.requireNonNull(foregroundColor);
            return this;
        }

        public Builder italic(boolean italic) {
            this.italic = italic;
            return this;
        }

        public Builder bold(boolean bold) {
            this.bold = bold;
            return this;
        }

        public DateTimeStyle build() {
            DateTimeStyle dateTimeStyle = new DateTimeStyle();
            dateTimeStyle.backgroundColor = this.backgroundColor;
            dateTimeStyle.foregroundColor = this.foregroundColor;
            dateTimeStyle.italic = this.italic;
            dateTimeStyle.bold = this.bold;
            return dateTimeStyle;
        }

    }
}
