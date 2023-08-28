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

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorIfDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the form description If component.
 *
 * @author pcdavid
 */
public record FormDescriptionEditorIfComponentProps(VariableManager variableManager, FormDescriptionEditorIfDescription formDescriptionEditorIfDescription, List<IWidgetDescriptor> widgetDescriptors)
        implements IProps {

    public FormDescriptionEditorIfComponentProps(VariableManager variableManager, FormDescriptionEditorIfDescription formDescriptionEditorIfDescription, List<IWidgetDescriptor> widgetDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.formDescriptionEditorIfDescription = Objects.requireNonNull(formDescriptionEditorIfDescription);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
    }

}
