/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.ButtonStyle.Builder;
import org.eclipse.sirius.components.view.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.FixedColor;

/**
 * The style provider for the Button Description widget of the View DSL. This only handles "static" or "preview" styles
 * which can be computed for "detached" widgets in the FormDescriptionEditor.
 *
 * @author arichard
 */
public class ButtonStyleProvider {

    private final ButtonDescriptionStyle viewStyle;

    public ButtonStyleProvider(ButtonDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    public ButtonStyle build() {
        Builder buttonStyleBuilder = ButtonStyle.newButtonStyle();

        if (this.viewStyle.getBackgroundColor() instanceof FixedColor fixedColor) {
            String backgroundColor = fixedColor.getValue();
            if (backgroundColor != null && !backgroundColor.isBlank()) {
                buttonStyleBuilder.backgroundColor(backgroundColor);
            }
        }

        if (this.viewStyle.getForegroundColor() instanceof FixedColor fixedColor) {
            String foregroundColor = fixedColor.getValue();
            if (foregroundColor != null && !foregroundColor.isBlank()) {
                buttonStyleBuilder.foregroundColor(foregroundColor);
            }
        }

        int fontSize = this.viewStyle.getFontSize();
        boolean italic = this.viewStyle.isItalic();
        boolean bold = this.viewStyle.isBold();
        boolean underline = this.viewStyle.isUnderline();
        boolean strikeThrough = this.viewStyle.isStrikeThrough();

        // @formatter:off
        return buttonStyleBuilder
                .fontSize(fontSize)
                .italic(italic)
                .bold(bold)
                .underline(underline)
                .strikeThrough(strikeThrough)
                .build();
        // @formatter:on
    }

}
