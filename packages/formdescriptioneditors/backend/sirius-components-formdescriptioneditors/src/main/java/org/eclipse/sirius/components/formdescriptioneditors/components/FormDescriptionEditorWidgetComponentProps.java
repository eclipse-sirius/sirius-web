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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * * The properties of the form description editor widget component.
 *
 * @author pcdavid
 */
public record FormDescriptionEditorWidgetComponentProps(VariableManager variableManager, AbstractWidgetDescription abstractWidgetDescription, List<IWidgetDescriptor> widgetDescriptors) implements IProps {

    public FormDescriptionEditorWidgetComponentProps(VariableManager variableManager, AbstractWidgetDescription abstractWidgetDescription, List<IWidgetDescriptor> widgetDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.abstractWidgetDescription = Objects.requireNonNull(abstractWidgetDescription);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
    }
}
