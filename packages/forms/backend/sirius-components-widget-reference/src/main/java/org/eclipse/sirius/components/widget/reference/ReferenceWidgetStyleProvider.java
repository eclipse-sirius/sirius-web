/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;

/**
 * The style provider for the Reference Widget.
 *
 * @author frouene
 */
public class ReferenceWidgetStyleProvider implements Function<VariableManager, ReferenceWidgetStyle> {

    private final ReferenceWidgetDescriptionStyle viewStyle;

    public ReferenceWidgetStyleProvider(ReferenceWidgetDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    @Override
    public ReferenceWidgetStyle apply(VariableManager variableManager) {
        ReferenceWidgetStyle.Builder referenceWidgetStyle = ReferenceWidgetStyle.newReferenceWidgetStyle();

        if (this.viewStyle.getColor() instanceof FixedColor fixedColor) {
            String color = fixedColor.getValue();
            if (color != null && !color.isBlank()) {
                referenceWidgetStyle.color(color);
            }
        }

        int fontSize = this.viewStyle.getFontSize();
        boolean italic = this.viewStyle.isItalic();
        boolean bold = this.viewStyle.isBold();
        boolean underline = this.viewStyle.isUnderline();
        boolean strikeThrough = this.viewStyle.isStrikeThrough();

        return referenceWidgetStyle
                .fontSize(fontSize)
                .italic(italic)
                .bold(bold)
                .underline(underline)
                .strikeThrough(strikeThrough)
                .build();
    }

}
