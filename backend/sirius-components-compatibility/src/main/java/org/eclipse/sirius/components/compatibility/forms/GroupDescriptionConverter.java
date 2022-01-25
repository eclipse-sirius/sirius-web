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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * This class is used to convert a Sirius GroupDescription to an Sirius Web GroupDescription.
 *
 * @author fbarbin
 */
public class GroupDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    public GroupDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IIdentifierProvider identifierProvider,
            IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
    }

    public GroupDescription convert(org.eclipse.sirius.properties.GroupDescription siriusGroupDescription,
            Map<org.eclipse.sirius.properties.GroupDescription, GroupDescription> siriusGroup2SiriusWebGroup) {
        ControlDescriptionConverter controlDescriptionConverter = new ControlDescriptionConverter(this.interpreter, this.objectService, this.identifierProvider,
                this.modelOperationHandlerSwitchProvider);

        Function<VariableManager, String> idProvider = variableManager -> String.valueOf(siriusGroup2SiriusWebGroup.size());
        StringValueProvider labelProvider = new StringValueProvider(this.interpreter, Optional.ofNullable(siriusGroupDescription.getLabelExpression()).orElse("")); //$NON-NLS-1$

        // @formatter:off
        List<AbstractControlDescription> controlDescriptions = siriusGroupDescription.getControls().stream()
                .flatMap(controlDescription -> controlDescriptionConverter.convert(controlDescription).stream())
                .collect(Collectors.toUnmodifiableList());

        GroupDescription groupDescription = GroupDescription.newGroupDescription(this.identifierProvider.getIdentifier(siriusGroupDescription))
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
        // @formatter:on
        siriusGroup2SiriusWebGroup.put(siriusGroupDescription, groupDescription);
        return groupDescription;
    }
}
