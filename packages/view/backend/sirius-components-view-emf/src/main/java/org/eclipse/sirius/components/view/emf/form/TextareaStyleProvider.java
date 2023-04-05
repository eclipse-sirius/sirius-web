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
package org.eclipse.sirius.components.view.emf.form;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.forms.TextareaStyle.Builder;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;

/**
 * The style provider for the Textarea Description widget of the View DSL.
 *
 * @author arichard
 */
public class TextareaStyleProvider implements Function<VariableManager, TextareaStyle> {

    private final TextareaDescriptionStyle viewStyle;

    public TextareaStyleProvider(TextareaDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    @Override
    public TextareaStyle apply(VariableManager variableManager) {
        Builder textareaStyleBuilder = TextareaStyle.newTextareaStyle();

        if (this.viewStyle.getBackgroundColor() instanceof FixedColor fixedColor) {
            String backgroundColor = fixedColor.getValue();
            if (backgroundColor != null && !backgroundColor.isBlank()) {
                textareaStyleBuilder.backgroundColor(backgroundColor);
            }
        }

        if (this.viewStyle.getForegroundColor() instanceof FixedColor fixedColor) {
            String foregroundColor = fixedColor.getValue();
            if (foregroundColor != null && !foregroundColor.isBlank()) {
                textareaStyleBuilder.foregroundColor(foregroundColor);
            }
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
