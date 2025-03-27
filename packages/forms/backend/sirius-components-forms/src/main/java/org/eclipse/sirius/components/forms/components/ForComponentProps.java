/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;

/**
 * The properties of the for component.
 *
 * @author sbegaudeau
 */
public class ForComponentProps implements IProps {

    private final VariableManager variableManager;

    private final ForDescription forDescription;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<ICustomCellDescriptor> customCellDescriptors;

    public ForComponentProps(VariableManager variableManager, ForDescription forDescription, List<IWidgetDescriptor> widgetDescriptors, List<ICustomCellDescriptor> customCellDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.forDescription = Objects.requireNonNull(forDescription);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.customCellDescriptors = Objects.requireNonNull(customCellDescriptors);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ForDescription getForDescription() {
        return this.forDescription;
    }

    public List<IWidgetDescriptor> getWidgetDescriptors() {
        return this.widgetDescriptors;
    }

    public List<ICustomCellDescriptor> getCustomCellDescriptors() {
        return this.customCellDescriptors;
    }
}
