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

import org.eclipse.sirius.components.forms.SelectStyle;
import org.eclipse.sirius.components.forms.SelectStyle.Builder;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;

/**
 * The style provider for the Select Description widget of the View DSL. This only handles "static" or "preview" styles
 * which can be computed for "detached" widgets in the FormDescriptionEditor.
 *
 * @author arichard
 */
public class SelectStyleProvider {

    private final SelectDescriptionStyle viewStyle;

    public SelectStyleProvider(SelectDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    public SelectStyle build() {
        Builder selectStyleBuilder = SelectStyle.newSelectStyle();

        String backgroundColor = this.viewStyle.getBackgroundColor();
        if (backgroundColor != null && !backgroundColor.isBlank()) {
            selectStyleBuilder.backgroundColor(backgroundColor);
        }
        String foregroundColor = this.viewStyle.getForegroundColor();
        if (foregroundColor != null && !foregroundColor.isBlank()) {
            selectStyleBuilder.foregroundColor(foregroundColor);
        }
        int fontSize = this.viewStyle.getFontSize();
        boolean italic = this.viewStyle.isItalic();
        boolean bold = this.viewStyle.isBold();
        boolean underline = this.viewStyle.isUnderline();
        boolean strikeThrough = this.viewStyle.isStrikeThrough();

        // @formatter:off
        return selectStyleBuilder
                .fontSize(fontSize)
                .italic(italic)
                .bold(bold)
                .underline(underline)
                .strikeThrough(strikeThrough)
                .build();
        // @formatter:on
    }

}
