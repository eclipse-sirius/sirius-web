/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Aggregates all pages that can be created from the provided {@link FormDescription}s, according to all the given
 * objects.
 *
 * @author fbarbin
 */
public class FormDescriptionAggregator {

    public Optional<FormDescription> aggregate(List<FormDescription> formDescriptions, List<Object> objects, IObjectService objectService) {
        VariableManager pageVariableManager = new VariableManager();
        pageVariableManager.put(VariableManager.SELF, objects);

        // @formatter:off
        List<PageDescription> pageDescriptions = formDescriptions.stream()
                .flatMap(formDescription -> formDescription.getPageDescriptions().stream())
                .filter(pageDescription -> pageDescription.getCanCreatePredicate().test(pageVariableManager))
                .collect(Collectors.toList());
        // @formatter:on

        if (pageDescriptions.isEmpty()) {
            return Optional.empty();
        }

        // @formatter:off
        List<GroupDescription> groupDescriptions = pageDescriptions.stream()
                .flatMap(pageDescription -> pageDescription.getGroupDescriptions().stream())
                .collect(Collectors.toUnmodifiableList());

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .map(objectService::getFullLabel)
                .orElse("Properties");
        // @formatter:on

        // @formatter:off
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .map(objectService::getId)
                .orElse(null);

        return Optional.of(FormDescription.newFormDescription(PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
                .label("Aggregated form description")
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
