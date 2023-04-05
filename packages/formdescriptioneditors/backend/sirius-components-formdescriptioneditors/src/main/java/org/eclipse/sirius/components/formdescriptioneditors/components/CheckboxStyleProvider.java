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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.forms.CheckboxStyle.Builder;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.FixedColor;

/**
 * The style provider for the Checkbox Description widget of the View DSL. This only handles "static" or "preview"
 * styles which can be computed for "detached" widgets in the FormDescriptionEditor.
 *
 * @author arichard
 */
public class CheckboxStyleProvider {

    private final CheckboxDescriptionStyle viewStyle;

    public CheckboxStyleProvider(CheckboxDescriptionStyle viewStyle) {
        this.viewStyle = Objects.requireNonNull(viewStyle);
    }

    public CheckboxStyle build() {
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
