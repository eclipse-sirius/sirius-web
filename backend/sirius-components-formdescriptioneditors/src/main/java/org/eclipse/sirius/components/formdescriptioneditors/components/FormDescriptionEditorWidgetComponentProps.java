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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorWidget;
import org.eclipse.sirius.components.formdescriptioneditors.description.AbstractFormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the form description editor widget component.
 *
 * @author arichard
 */
public class FormDescriptionEditorWidgetComponentProps implements IProps {
    private VariableManager variableManager;

    private AbstractFormDescriptionEditorWidgetDescription widgetDescription;

    private Optional<FormDescriptionEditorWidget> optionalPreviousWidget;

    public FormDescriptionEditorWidgetComponentProps(VariableManager variableManager, AbstractFormDescriptionEditorWidgetDescription widgetDescription,
            Optional<FormDescriptionEditorWidget> optionalPreviousWidget) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.widgetDescription = Objects.requireNonNull(widgetDescription);
        this.optionalPreviousWidget = Objects.requireNonNull(optionalPreviousWidget);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public AbstractFormDescriptionEditorWidgetDescription getWidgetDescription() {
        return this.widgetDescription;
    }

    public Optional<FormDescriptionEditorWidget> getOptionalPreviousWidget() {
        return this.optionalPreviousWidget;
    }

}
