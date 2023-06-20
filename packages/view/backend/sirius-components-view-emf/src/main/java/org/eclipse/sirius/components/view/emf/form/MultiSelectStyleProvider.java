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

import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.MultiSelectStyle.Builder;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;

/**
 * The style provider for the MultiSelect Description widget of the View DSL.
 *
 * @author arichard
 */
public class MultiSelectStyleProvider implements Function<VariableManager, MultiSelectStyle> {

    private final MultiSelectDescriptionStyle viewStyle;

    public MultiSelectStyleProvider(MultiSelectDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    @Override
    public MultiSelectStyle apply(VariableManager variableManager) {
        Builder multiSelectStyleBuilder = MultiSelectStyle.newMultiSelectStyle();

        if (this.viewStyle.getBackgroundColor() instanceof FixedColor fixedColor) {
            String backgroundColor = fixedColor.getValue();
            if (backgroundColor != null && !backgroundColor.isBlank()) {
                multiSelectStyleBuilder.backgroundColor(backgroundColor);
            }
        }

        if (this.viewStyle.getForegroundColor() instanceof FixedColor fixedColor) {
            String foregroundColor = fixedColor.getValue();
            if (foregroundColor != null && !foregroundColor.isBlank()) {
                multiSelectStyleBuilder.foregroundColor(foregroundColor);
            }
        }

        int fontSize = this.viewStyle.getFontSize();
        boolean italic = this.viewStyle.isItalic();
        boolean bold = this.viewStyle.isBold();
        boolean underline = this.viewStyle.isUnderline();
        boolean strikeThrough = this.viewStyle.isStrikeThrough();
        boolean isShowIcon = this.viewStyle.isShowIcon();

        // @formatter:off
        return multiSelectStyleBuilder
                .fontSize(fontSize)
                .italic(italic)
                .bold(bold)
                .underline(underline)
                .strikeThrough(strikeThrough)
                .showIcon(isShowIcon)
                .build();
        // @formatter:on
    }

}
