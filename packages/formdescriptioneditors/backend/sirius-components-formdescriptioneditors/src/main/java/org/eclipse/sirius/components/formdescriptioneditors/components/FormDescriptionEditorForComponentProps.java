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

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorForDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the form description editor For component.
 *
 * @author pcdavid
 */
public record FormDescriptionEditorForComponentProps(VariableManager variableManager, FormDescriptionEditorForDescription formDescriptionEditorForDescription, List<IWidgetDescriptor> widgetDescriptors) implements IProps {

    public FormDescriptionEditorForComponentProps(VariableManager variableManager, FormDescriptionEditorForDescription formDescriptionEditorForDescription, List<IWidgetDescriptor> widgetDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.formDescriptionEditorForDescription = Objects.requireNonNull(formDescriptionEditorForDescription);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
    }

}
