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
package org.eclipse.sirius.web.spring.collaborative.forms;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.forms.description.GroupDescription;
import org.eclipse.sirius.web.forms.description.PageDescription;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Aggregates all pages that can be created from the provided {@link FormDescription}s, according to the given object
 * context.
 *
 * @author fbarbin
 */
public class FormDescriptionAggregator {

    public Optional<FormDescription> aggregate(List<FormDescription> formDescriptions, Object object, IObjectService objectService) {
        VariableManager pageVariableManager = new VariableManager();
        pageVariableManager.put(VariableManager.SELF, object);

        // @formatter:off
        List<PageDescription> pageDescriptions = formDescriptions.stream()
                .flatMap(formDescription -> formDescription.getPageDescriptions().stream())
                .filter(pageDescription -> pageDescription.getCanCreatePredicate().test(pageVariableManager))
                .collect(Collectors.toList());

        if (pageDescriptions.isEmpty()) {
            return Optional.empty();
        }

        List<GroupDescription> groupDescriptions = pageDescriptions.stream()
                .flatMap(pageDescription -> pageDescription.getGroupDescriptions().stream())
                .filter(groupDescription -> groupDescription.getCanCreatePredicate().test(pageVariableManager))
                .collect(Collectors.toUnmodifiableList());


        Function<VariableManager, String> labelProvider = variableManager -> {
            return Optional.ofNullable(variableManager.getVariables().get(VariableManager.SELF))
                    .map(objectService::getFullLabel)
                    .orElse("Properties"); //$NON-NLS-1$
        };

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            return Optional.ofNullable(object).map(objectService::getId).orElse(null);
        };

        return Optional.of(FormDescription.newFormDescription(UUID.randomUUID())
                .label("Aggregated form description") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(pageDescriptions)
                .groupDescriptions(groupDescriptions)
                .build());
        // @formatter:on
    }
}
