/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form.converters.widgets;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert an image description.
 *
 * @author sbegaudeau
 */
@Service
public class ImageDescriptionConverter implements IWidgetDescriptionConverter {

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    public ImageDescriptionConverter(IObjectService objectService, IFormIdProvider widgetIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.ImageDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.ImageDescription viewImageDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewImageDescription);

            var imageDescription = ImageDescription.newImageDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                    .labelProvider(new StringValueProvider(interpreter, viewImageDescription.getLabelExpression()))
                    .urlProvider(new StringValueProvider(interpreter, viewImageDescription.getUrlExpression()))
                    .maxWidthProvider(new StringValueProvider(interpreter, viewImageDescription.getMaxWidthExpression()))
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewImageDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewImageDescription.getHelpExpression()))
                    .build();

            return Optional.of(imageDescription);
        }
        return Optional.empty();
    }
}
