/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the widget component.
 *
 * @author sbegaudeau
 */
public class WidgetComponentProps implements IProps {
    private VariableManager variableManager;

    private AbstractWidgetDescription widgetDescription;

    private final List<IWidgetDescriptor> widgetDescriptors;

    public WidgetComponentProps(VariableManager variableManager, AbstractWidgetDescription widgetDescription, List<IWidgetDescriptor> widgetDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.widgetDescription = Objects.requireNonNull(widgetDescription);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public AbstractWidgetDescription getWidgetDescription() {
        return this.widgetDescription;
    }

    public List<IWidgetDescriptor> getWidgetDescriptors() {
        return this.widgetDescriptors;
    }
}
