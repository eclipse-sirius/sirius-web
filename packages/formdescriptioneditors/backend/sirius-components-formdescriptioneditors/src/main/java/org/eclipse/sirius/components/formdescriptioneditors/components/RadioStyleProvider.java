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

import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.forms.RadioStyle.Builder;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;

/**
 * The style provider for the Radio Description widget of the View DSL. This only handles "static" or "preview" styles
 * which can be computed for "detached" widgets in the FormDescriptionEditor.
 *
 * @author arichard
 */
public class RadioStyleProvider {

    private final RadioDescriptionStyle viewStyle;

    public RadioStyleProvider(RadioDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    public RadioStyle build() {
        Builder radioStyleBuilder = RadioStyle.newRadioStyle();

        String color = this.viewStyle.getColor();
        if (color != null && !color.isBlank()) {
            radioStyleBuilder.color(color);
        }
        int fontSize = this.viewStyle.getFontSize();
        boolean italic = this.viewStyle.isItalic();
        boolean bold = this.viewStyle.isBold();
        boolean underline = this.viewStyle.isUnderline();
        boolean strikeThrough = this.viewStyle.isStrikeThrough();

        // @formatter:off
        return radioStyleBuilder
                .fontSize(fontSize)
                .italic(italic)
                .bold(bold)
                .underline(underline)
                .strikeThrough(strikeThrough)
                .build();
        // @formatter:on
    }

}
