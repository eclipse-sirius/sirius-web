/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.forms.api.IFormCreationService;
import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.collaborative.forms.variables.FormVariableProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to create form representations.
 *
 * @author sbegaudeau
 */
@Service
public class FormCreationService implements IFormCreationService {

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final IFormPostProcessor formPostProcessor;

    public FormCreationService(List<IWidgetDescriptor> widgetDescriptors, Optional<IFormPostProcessor> optionalFormPostProcessor) {
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.formPostProcessor = Objects.requireNonNull(optionalFormPostProcessor).orElse(new IFormPostProcessor.NoOp());
    }

    @Override
    public Form create(IEditingContext editingContext, FormDescription formDescription, Object object, FormContext formContext) {
        var variableManager = new VariableManager();
        variableManager.put(RepresentationVariables.SELF.name(), object);
        variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
        variableManager.put(FormVariableProvider.SELECTION.name(), formContext.selection());
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, formContext.id());

        var initializedVariableManager = formDescription.getVariableManagerInitializer().apply(variableManager);
        var formComponentProps = new FormComponentProps(initializedVariableManager, formDescription, this.widgetDescriptors);
        var element = new Element(FormComponent.class, formComponentProps);
        var form = new FormRenderer(this.widgetDescriptors).render(element);
        return this.formPostProcessor.postProcess(form, initializedVariableManager);
    }
}
