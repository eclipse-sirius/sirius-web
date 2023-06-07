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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.forms.variables.FormVariableProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.FormDescription;
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


    public Optional<FormDescription> aggregate(List<PageDescription> pageDescriptions, List<Object> objects, IObjectService objectService) {
        List<PageDescription> eligiblePageDescriptions = new ArrayList<>();


        if (!objects.isEmpty()) {
            VariableManager pageVariableManager = new VariableManager();
            pageVariableManager.put(VariableManager.SELF, objects.get(0));
            pageVariableManager.put(FormVariableProvider.SELECTION.name(), objects);

            eligiblePageDescriptions.addAll(pageDescriptions.stream()
                    .filter(pageDescription -> pageDescription.getCanCreatePredicate().test(pageVariableManager))
                    .toList());
        }


        if (eligiblePageDescriptions.isEmpty()) {
            return Optional.empty();
        }

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(objectService::getFullLabel)
                .orElse("Properties");

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(objectService::getId)
                .orElse(null);

        return Optional.of(FormDescription.newFormDescription(PropertiesEventProcessorFactory.DETAILS_VIEW_ID)
                .label("Aggregated form description")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(eligiblePageDescriptions)
                .build());
    }
}
