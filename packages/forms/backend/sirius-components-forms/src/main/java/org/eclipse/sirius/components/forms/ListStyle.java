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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The style of a List Widget.
 *
 * @author fbarbin
 */
@Immutable
public final class ListStyle extends AbstractFontStyle {

    private String color;

    private ListStyle() {
        // Prevent instantiation
    }

    public String getColor() {
        return this.color;
    }

    public static Builder newListStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'color: {1}, fontSize: {2}, italic: {3}, bold: {4}, underline: {5}, strikeThrough: {6},'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.color, this.fontSize, this.italic, this.bold, this.underline, this.strikeThrough);
    }

    /**
     * Builder used to create the List style.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String color;

        private int fontSize;

        private boolean italic;

        private boolean bold;

        private boolean underline;

        private boolean strikeThrough;

        private Builder() {
        }

        public Builder color(String color) {
            this.color = Objects.requireNonNull(color);
            return this;
        }

        public Builder fontSize(int fontSize) {
            this.fontSize = fontSize;
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

        public Builder underline(boolean underline) {
            this.underline = underline;
            return this;
        }

        public Builder strikeThrough(boolean strikeThrough) {
            this.strikeThrough = strikeThrough;
            return this;
        }

        public ListStyle build() {
            ListStyle listStyle = new ListStyle();
            listStyle.color = this.color;
            listStyle.fontSize = this.fontSize;
            listStyle.italic = this.italic;
            listStyle.bold = this.bold;
            listStyle.underline = this.underline;
            listStyle.strikeThrough = this.strikeThrough;
            return listStyle;
        }

    }
}
