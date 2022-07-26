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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * This class is used to convert a Sirius PageDescription to an Sirius Web PageDescription.
 *
 * @author fbarbin
 */
public class PageDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IIdentifierProvider identifierProvider;

    private final ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory;

    public PageDescriptionConverter(AQLInterpreter interpreter, IIdentifierProvider identifierProvider, ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.semanticCandidatesProviderFactory = Objects.requireNonNull(semanticCandidatesProviderFactory);
    }

    public PageDescription convert(org.eclipse.sirius.properties.PageDescription siriusPageDescription,
            Map<org.eclipse.sirius.properties.GroupDescription, GroupDescription> siriusGroup2SiriusWebGroup) {
        StringValueProvider labelProvider = new StringValueProvider(this.interpreter, siriusPageDescription.getLabelExpression());

        String domainClass = Optional.ofNullable(siriusPageDescription.getDomainClass()).orElse(""); //$NON-NLS-1$
        String semanticCandidatesExpression = Optional.ofNullable(siriusPageDescription.getSemanticCandidateExpression()).orElse(""); //$NON-NLS-1$
        String preconditionExpression = Optional.ofNullable(siriusPageDescription.getPreconditionExpression()).orElse(""); //$NON-NLS-1$
        var semanticCandidatesProvider = this.semanticCandidatesProviderFactory.getSemanticCandidatesProvider(this.interpreter, domainClass, semanticCandidatesExpression, preconditionExpression);

        Predicate<VariableManager> canCreatePredicate = (variableManager) -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            if (object != null) {
                return !semanticCandidatesProvider.apply(variableManager).isEmpty();
            }
            return false;
        };

        // @formatter:off
        List<GroupDescription> groupDescriptions = siriusPageDescription.getGroups().stream()
                .map(siriusGroup2SiriusWebGroup::get)
                .collect(Collectors.toUnmodifiableList());

        return PageDescription.newPageDescription(this.identifierProvider.getIdentifier(siriusPageDescription))
                .idProvider(labelProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(semanticCandidatesProvider)
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(canCreatePredicate)
                .build();
        // @formatter:on

    }
}
