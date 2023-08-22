/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.compatibility.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.utils.BooleanValueProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.properties.DynamicMappingIfDescription;

/**
 * This class is used to convert a Sirius {@link DynamicMappingIfDescription} to an Sirius Web {@link IfDescription}.
 *
 * @author fbarbin
 */
public class IfDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    public IfDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IIdentifierProvider identifierProvider,
            IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
    }

    public Optional<IfDescription> convert(org.eclipse.sirius.properties.DynamicMappingIfDescription siriusIfDescription) {
        BooleanValueProvider predicate = new BooleanValueProvider(this.interpreter, siriusIfDescription.getPredicateExpression());
        WidgetDescriptionConverter converter = new WidgetDescriptionConverter(this.interpreter, this.objectService, this.identifierProvider, this.modelOperationHandlerSwitchProvider);

        Optional<AbstractWidgetDescription> optionalWidgetDescription = converter.convert(siriusIfDescription.getWidget());
        return optionalWidgetDescription.map(widgetDescription -> {
            // @formatter:off
            return IfDescription.newIfDescription(this.identifierProvider.getIdentifier(siriusIfDescription))
                    .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                    .predicate(predicate)
                    .controlDescriptions(List.of(widgetDescription))
                    .build();
            // @formatter:on
        });

    }
}
