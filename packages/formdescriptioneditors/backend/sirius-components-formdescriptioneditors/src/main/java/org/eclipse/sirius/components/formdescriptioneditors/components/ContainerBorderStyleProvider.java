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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.ContainerBorderLineStyle;
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.view.FixedColor;

/**
 * The style provider for the Border Style of the View DSL. This only handles "static" or "preview" styles.
 *
 * @author frouene
 */
public class ContainerBorderStyleProvider {

    private final org.eclipse.sirius.components.view.form.ContainerBorderStyle viewStyle;

    public ContainerBorderStyleProvider(org.eclipse.sirius.components.view.form.ContainerBorderStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    public ContainerBorderStyle build() {
        ContainerBorderStyle.Builder containerBorderStyle = ContainerBorderStyle
                .newContainerBorderStyle()
                .size(this.viewStyle.getBorderSize())
                .radius(this.viewStyle.getBorderRadius())
                .lineStyle(ContainerBorderLineStyle.valueOf(this.viewStyle.getBorderLineStyle().getLiteral()));

        if (this.viewStyle.getBorderColor() instanceof FixedColor fixedColor) {
            String color = fixedColor.getValue();
            if (color != null && !color.isBlank()) {
                containerBorderStyle.color(color);
            }
        }


        return containerBorderStyle.build();
    }
}
