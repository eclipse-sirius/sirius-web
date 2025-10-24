/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.widget.reference;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IReferenceWidgetBehaviorConverter;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IReferenceWidgetPropertiesConverter;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based ReferenceWidgetDescription into its API equivalent.
 *
 * @author pcdavid
 */
@Service
public class ReferenceWidgetDescriptionConverter implements IWidgetDescriptionConverter {

    private final Logger logger = LoggerFactory.getLogger(ReferenceWidgetDescriptionConverter.class);

    private final IFormIdProvider widgetIdProvider;

    private final IReferenceWidgetPropertiesConverter referenceWidgetPropertiesConverter;

    private final IReferenceWidgetBehaviorConverter referenceWidgetBehaviorConverter;

    public ReferenceWidgetDescriptionConverter(IFormIdProvider widgetIdProvider, IReferenceWidgetPropertiesConverter referenceWidgetPropertiesConverter, IReferenceWidgetBehaviorConverter referenceWidgetBehaviorConverter) {
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.referenceWidgetPropertiesConverter = Objects.requireNonNull(referenceWidgetPropertiesConverter);
        this.referenceWidgetBehaviorConverter = Objects.requireNonNull(referenceWidgetBehaviorConverter);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof ReferenceWidgetDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof ReferenceWidgetDescription referenceDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(referenceDescription);

            if (referenceDescription.getReferenceNameExpression().isEmpty()) {
                this.logger.warn("Invalid empty Reference Name Expression on widget {}", referenceDescription.getName());
            } else {
                var builder = org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription.newReferenceWidgetDescription(descriptionId);

                this.referenceWidgetPropertiesConverter.convert(builder, referenceDescription, interpreter);
                this.referenceWidgetBehaviorConverter.convert(builder, referenceDescription, interpreter);

                var referenceWidgetDescription = builder.build();

                return Optional.of(referenceWidgetDescription);
            }
        }
        return Optional.empty();
    }

}
