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
package org.eclipse.sirius.components.forms.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Chart components props.
 *
 * @author fbarbin
 */
public class ChartWidgetComponentProps implements IProps {
    private final VariableManager variableManager;

    private final ChartWidgetDescription chartWidgetDescription;

    public ChartWidgetComponentProps(VariableManager variableManager, ChartWidgetDescription chartWidgetDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.chartWidgetDescription = Objects.requireNonNull(chartWidgetDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ChartWidgetDescription getChartWidgetDescription() {
        return this.chartWidgetDescription;
    }

}
