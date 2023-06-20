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

import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.forms.CheckboxStyle.Builder;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;

/**
 * The style provider for the Checkbox Description widget of the View DSL.
 *
 * @author arichard
 */
public class CheckboxStyleProvider implements Function<VariableManager, CheckboxStyle> {

    private final CheckboxDescriptionStyle viewStyle;

    public CheckboxStyleProvider(CheckboxDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    @Override
    public CheckboxStyle apply(VariableManager variableManager) {
        Builder checkboxStyleBuilder = CheckboxStyle.newCheckboxStyle();

        if (this.viewStyle.getColor() instanceof FixedColor fixedColor) {
            String color = fixedColor.getValue();
            if (color != null && !color.isBlank()) {
                checkboxStyleBuilder.color(color);
            }
        }


        return checkboxStyleBuilder.build();
    }

}
