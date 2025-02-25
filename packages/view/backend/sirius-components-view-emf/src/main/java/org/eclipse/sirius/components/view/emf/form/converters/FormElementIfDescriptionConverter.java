/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form.converters;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.BooleanValueProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverterSwitch;
import org.eclipse.sirius.components.view.form.FormElementIf;

/**
 * Used to convert the form element if.
 *
 * @author sbegaudeau
 */
public class FormElementIfDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    private final ViewFormDescriptionConverterSwitch viewFormDescriptionConverterSwitch;

    public FormElementIfDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IFormIdProvider widgetIdProvider, ViewFormDescriptionConverterSwitch viewFormDescriptionConverterSwitch) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.viewFormDescriptionConverterSwitch = Objects.requireNonNull(viewFormDescriptionConverterSwitch);
    }

    public IfDescription convert(FormElementIf formElementIf) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(formElementIf);
        return IfDescription.newIfDescription(descriptionId)
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .predicate(new BooleanValueProvider(this.interpreter, formElementIf.getPredicateExpression()))
                .controlDescriptions(formElementIf.getChildren().stream()
                        .map(this.viewFormDescriptionConverterSwitch::doSwitch)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList())
                .build();
    }
}
