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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.compat.services.representations.IdentifierProvider;
import org.eclipse.sirius.web.compat.utils.StringValueProvider;
import org.eclipse.sirius.web.forms.description.AbstractControlDescription;
import org.eclipse.sirius.web.forms.description.GroupDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * This class is used to convert a Sirius GroupDescription to an Sirius Web GroupDescription.
 *
 * @author fbarbin
 */
public class GroupDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IdentifierProvider identifierProvider;

    public GroupDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IdentifierProvider identifierProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
    }

    public GroupDescription convert(org.eclipse.sirius.properties.GroupDescription siriusGroupDescription,
            Map<org.eclipse.sirius.properties.GroupDescription, GroupDescription> siriusGroup2SiriusWebGroup) {
        ControlDescriptionConverter controlDescriptionConverter = new ControlDescriptionConverter(this.interpreter, this.objectService, this.identifierProvider);

        // @formatter:off
        Supplier<String> fallbackIdProvider = () -> String.valueOf(siriusGroup2SiriusWebGroup.size());

        Function<VariableManager, String> idProvider = variableManager -> {
            var optionalEObject = Optional.of(variableManager.getVariables().get(VariableManager.SELF))
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast);

            var optionalLabel = this.interpreter.evaluateExpression(variableManager.getVariables(), siriusGroupDescription.getLabelExpression()).asString();

            return optionalEObject.flatMap(eObject -> {
                return optionalLabel.map(label -> EcoreUtil.getURI(eObject) + label);
            }).orElseGet(fallbackIdProvider);
        };
        // @formatter:on

        StringValueProvider labelProvider = new StringValueProvider(this.interpreter, siriusGroupDescription.getLabelExpression());

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
