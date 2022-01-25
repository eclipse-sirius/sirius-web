/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.properties.DynamicMappingForDescription;

/**
 * This class is used to convert a Sirius {@link DynamicMappingForDescription} to an Sirius Web {@link ForDescription}.
 *
 * @author fbarbin
 */
public class ForDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    public ForDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IIdentifierProvider identifierProvider,
            IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
    }

    public ForDescription convert(org.eclipse.sirius.properties.DynamicMappingForDescription siriusForDescription) {
        // @formatter:off
        Function<VariableManager, List<Object>> iterableProvider = (variableManager) -> {
            return this.interpreter.evaluateExpression(variableManager.getVariables(), siriusForDescription.getIterableExpression()).asObjects()
                    .orElse(Collections.emptyList());
        };
        IfDescriptionConverter converter = new IfDescriptionConverter(this.interpreter, this.objectService, this.identifierProvider, this.modelOperationHandlerSwitchProvider);
        List<IfDescription> ifDescriptions = siriusForDescription.getIfs().stream()
                .flatMap(ifDescription -> converter.convert(ifDescription).stream())
                .collect(Collectors.toUnmodifiableList());

       return ForDescription.newForDescription(this.identifierProvider.getIdentifier(siriusForDescription))
               .iterableProvider(iterableProvider)
               .iterator(siriusForDescription.getIterator())
               .ifDescriptions(ifDescriptions)
               .build();
        // @formatter:on

    }
}
