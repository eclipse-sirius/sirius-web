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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.forms.TextareaStyle.Builder;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;

/**
 * The style provider for the Textarea Description widget of the View DSL. This only handles "static" or "preview"
 * styles which can be computed for "detached" widgets in the FormDescriptionEditor.
 *
 * @author arichard
 */
public class TextareaStyleProvider {

    private final TextareaDescriptionStyle viewStyle;

    public TextareaStyleProvider(TextareaDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    public TextareaStyle build() {
        Builder textareaStyleBuilder = TextareaStyle.newTextareaStyle();

        String backgroundColor = this.viewStyle.getBackgroundColor();
        if (backgroundColor != null && !backgroundColor.isBlank()) {
            textareaStyleBuilder.backgroundColor(backgroundColor);
        }
        String foregroundColor = this.viewStyle.getForegroundColor();
        if (foregroundColor != null && !foregroundColor.isBlank()) {
            textareaStyleBuilder.foregroundColor(foregroundColor);
        }
        int fontSize = this.viewStyle.getFontSize();
        boolean italic = this.viewStyle.isItalic();
        boolean bold = this.viewStyle.isBold();
        boolean underline = this.viewStyle.isUnderline();
        boolean strikeThrough = this.viewStyle.isStrikeThrough();

        // @formatter:off
        return textareaStyleBuilder
                .fontSize(fontSize)
                .italic(italic)
                .bold(bold)
                .underline(underline)
                .strikeThrough(strikeThrough)
                .build();
        // @formatter:on
    }

}
