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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetPreviewConverterProvider;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * * The properties of the form description editor component.
 *
 * @author arichard
 */
public class FormDescriptionEditorComponentProps implements IProps {

    private final VariableManager variableManager;

    private final FormDescriptionEditorDescription formDescriptionEditorDescription;

    private final Optional<FormDescriptionEditor> optionalPreviousFormDescriptionEditor;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IWidgetPreviewConverterProvider> customWidgetConverterProviders;

    public FormDescriptionEditorComponentProps(VariableManager variableManager, FormDescriptionEditorDescription formDescriptionEditorDescription,
            Optional<FormDescriptionEditor> optionalPreviousFormDescriptionEditor, List<IWidgetDescriptor> widgetDescriptors, List<IWidgetPreviewConverterProvider> customWidgetConverterProviders) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.formDescriptionEditorDescription = Objects.requireNonNull(formDescriptionEditorDescription);
        this.optionalPreviousFormDescriptionEditor = Objects.requireNonNull(optionalPreviousFormDescriptionEditor);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.customWidgetConverterProviders = Objects.requireNonNull(customWidgetConverterProviders);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public FormDescriptionEditorDescription getFormDescriptionEditorDescription() {
        return this.formDescriptionEditorDescription;
    }

    public Optional<FormDescriptionEditor> getOptionalPreviousFormDescriptionEditor() {
        return this.optionalPreviousFormDescriptionEditor;
    }

    public List<IWidgetPreviewConverterProvider> getCustomWidgetConverterProviders() {
        return this.customWidgetConverterProviders;
    }

    public List<IWidgetDescriptor> getWidgetDescriptors() {
        return this.widgetDescriptors;
    }
}
