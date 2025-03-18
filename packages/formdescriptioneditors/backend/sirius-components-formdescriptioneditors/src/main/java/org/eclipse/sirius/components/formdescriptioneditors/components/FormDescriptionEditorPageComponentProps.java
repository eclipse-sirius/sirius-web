/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import org.eclipse.sirius.components.formdescriptioneditors.IWidgetPreviewConverterProvider;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;

/**
 * * The properties of the form description editor page component.
 *
 * @author arichard
 */
public record FormDescriptionEditorPageComponentProps(VariableManager variableManager, FormDescriptionEditorDescription formDescriptionEditorDescription, List<IWidgetDescriptor> widgetDescriptors,
        List<IWidgetPreviewConverterProvider> customWidgetConverterProviders, List<ICustomCellDescriptor> customCellDescriptors) implements IProps {

    public FormDescriptionEditorPageComponentProps(VariableManager variableManager, FormDescriptionEditorDescription formDescriptionEditorDescription, List<IWidgetDescriptor> widgetDescriptors,
            List<IWidgetPreviewConverterProvider> customWidgetConverterProviders, List<ICustomCellDescriptor> customCellDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.formDescriptionEditorDescription = Objects.requireNonNull(formDescriptionEditorDescription);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.customWidgetConverterProviders = Objects.requireNonNull(customWidgetConverterProviders);
        this.customCellDescriptors = Objects.requireNonNull(customCellDescriptors);
    }
}
