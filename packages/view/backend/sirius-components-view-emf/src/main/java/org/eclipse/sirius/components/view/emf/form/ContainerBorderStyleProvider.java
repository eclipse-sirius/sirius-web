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
package org.eclipse.sirius.components.view.emf.form;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.ContainerBorderLineStyle;
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;

/**
 * The style provider for a container of the View DSL.
 *
 * @author frouene
 */
public class ContainerBorderStyleProvider implements Function<VariableManager, ContainerBorderStyle> {

    private final org.eclipse.sirius.components.view.form.ContainerBorderStyle viewStyle;

    public ContainerBorderStyleProvider(org.eclipse.sirius.components.view.form.ContainerBorderStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    @Override
    public ContainerBorderStyle apply(VariableManager variableManager) {
        ContainerBorderStyle.Builder styleBuilder = ContainerBorderStyle.newContainerBorderStyle()
                .radius(this.viewStyle.getBorderRadius())
                .size(this.viewStyle.getBorderSize());

        if (this.viewStyle.getBorderColor() instanceof FixedColor fixedColor) {
            String color = fixedColor.getValue();
            if (color != null && !color.isBlank()) {
                styleBuilder.color(color);
            }
        }
        if (this.viewStyle.getBorderLineStyle() != null) {
            styleBuilder.lineStyle(ContainerBorderLineStyle.valueOf(this.viewStyle.getBorderLineStyle().getLiteral()));
        }

        return styleBuilder.build();
    }
}
