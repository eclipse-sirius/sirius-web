/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.forms;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.properties.DynamicMappingIfDescription;
import org.eclipse.sirius.web.compat.services.representations.IdentifierProvider;
import org.eclipse.sirius.web.compat.utils.BooleanValueProvider;
import org.eclipse.sirius.web.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.web.forms.description.IfDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * This class is used to convert a Sirius {@link DynamicMappingIfDescription} to an Sirius Web {@link IfDescription}.
 *
 * @author fbarbin
 */
public class IfDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IdentifierProvider identifierProvider;

    public IfDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IdentifierProvider identifierProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
    }

    public Optional<IfDescription> convert(org.eclipse.sirius.properties.DynamicMappingIfDescription siriusIfDescription) {
        BooleanValueProvider predicate = new BooleanValueProvider(this.interpreter, siriusIfDescription.getPredicateExpression());
        WidgetDescriptionConverter converter = new WidgetDescriptionConverter(this.interpreter, this.objectService, this.identifierProvider);

        Optional<AbstractWidgetDescription> optionalWidgetDescription = converter.convert(siriusIfDescription.getWidget());
        return optionalWidgetDescription.map(widgetDescription -> {
            // @formatter:off
            return IfDescription.newIfDescription(this.identifierProvider.getIdentifier(siriusIfDescription))
                    .predicate(predicate)
                    .widgetDescription(widgetDescription)
                    .build();
            // @formatter:on
        });

    }
}
