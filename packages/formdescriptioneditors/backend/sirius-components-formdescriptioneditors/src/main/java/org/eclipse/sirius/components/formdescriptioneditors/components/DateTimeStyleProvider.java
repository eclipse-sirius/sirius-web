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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.DateTimeStyle;
import org.eclipse.sirius.components.forms.DateTimeStyle.Builder;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;

/**
 * The style provider for the DateTime Description widget of the View DSL. This only handles "static" or "preview"
 * styles which can be computed for "detached" widgets in the FormDescriptionEditor.
 *
 * @author lfasani
 */
public class DateTimeStyleProvider {

    private final DateTimeDescriptionStyle viewStyle;

    public DateTimeStyleProvider(DateTimeDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    public DateTimeStyle build() {
        Builder dateTimeStyleBuilder = DateTimeStyle.newDateTimeStyle();

        if (this.viewStyle.getBackgroundColor() instanceof FixedColor fixedColor) {
            String backgroundColor = fixedColor.getValue();
            if (backgroundColor != null && !backgroundColor.isBlank()) {
                dateTimeStyleBuilder.backgroundColor(backgroundColor);
            }
        }

        if (this.viewStyle.getForegroundColor() instanceof FixedColor fixedColor) {
            String foregroundColor = fixedColor.getValue();
            if (foregroundColor != null && !foregroundColor.isBlank()) {
                dateTimeStyleBuilder.foregroundColor(foregroundColor);
            }
        }

        boolean italic = this.viewStyle.isItalic();
        boolean bold = this.viewStyle.isBold();

        return dateTimeStyleBuilder
                .italic(italic)
                .bold(bold)
                .build();
    }

}
