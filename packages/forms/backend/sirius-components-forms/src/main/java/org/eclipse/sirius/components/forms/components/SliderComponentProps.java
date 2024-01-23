/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.description.SliderDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the slider component.
 * @author pcdavid
 */
public class SliderComponentProps implements IProps {
    private final VariableManager variableManager;

    private final SliderDescription sliderDescription;

    public SliderComponentProps(VariableManager variableManager, SliderDescription sliderDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.sliderDescription = Objects.requireNonNull(sliderDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public SliderDescription getSliderDescription() {
        return this.sliderDescription;
    }
}
