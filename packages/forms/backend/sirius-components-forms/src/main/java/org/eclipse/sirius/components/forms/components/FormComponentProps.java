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

import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;

/**
 * The properties of the form component.
 *
 * @author sbegaudeau
 */
public class FormComponentProps implements IProps {
    
    private final VariableManager variableManager;

    private final FormDescription formDescription;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<ICustomCellDescriptor> customCellDescriptors;

    public FormComponentProps(VariableManager variableManager, FormDescription formDescription, List<IWidgetDescriptor> widgetDescriptors, List<ICustomCellDescriptor> customCellDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.formDescription = Objects.requireNonNull(formDescription);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.customCellDescriptors = Objects.requireNonNull(customCellDescriptors);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public FormDescription getFormDescription() {
        return this.formDescription;
    }

    public List<IWidgetDescriptor> getWidgetDescriptors() {
        return this.widgetDescriptors;
    }

    public List<ICustomCellDescriptor> getCustomCellDescriptors() {
        return this.customCellDescriptors;
    }
}
