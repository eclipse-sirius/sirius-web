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
package org.eclipse.sirius.components.view.emf.form;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.DateTimeStyle;
import org.eclipse.sirius.components.forms.DateTimeStyle.Builder;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;

/**
 * The style provider for the DateTime Description widget of the View DSL.
 *
 * @author lfasani
 */
public class DateTimeStyleProvider implements Function<VariableManager, DateTimeStyle> {

    private final DateTimeDescriptionStyle viewStyle;

    public DateTimeStyleProvider(DateTimeDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    @Override
    public DateTimeStyle apply(VariableManager variableManager) {
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
