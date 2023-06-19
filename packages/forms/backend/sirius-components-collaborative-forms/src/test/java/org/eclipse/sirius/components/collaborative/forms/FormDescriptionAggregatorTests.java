/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.junit.jupiter.api.Test;

/**
 * Test Cases for the {@link FormDescriptionAggregator}.
 *
 * @author fbarbin
 */
public class FormDescriptionAggregatorTests {

    private static final String LABEL = "Label";

    @Test
    public void testAggregatorWithMatchingPages() {
        Object object = new Object();
        List<PageDescription> pageDescriptions = new ArrayList<>();

        FormDescription formDescription = this.createForm(object, 3, 2);
        FormDescription formDescription2 = this.createForm(object, 3, 1);

        pageDescriptions.addAll(formDescription.getPageDescriptions());
        pageDescriptions.addAll(formDescription2.getPageDescriptions());

        FormDescriptionAggregator aggregator = new FormDescriptionAggregator();
        Optional<FormDescription> optional = aggregator.aggregate(pageDescriptions, List.of(object), new IObjectService.NoOp());

        assertThat(optional).isPresent();
        assertThat(optional.get().getPageDescriptions()).hasSize(3);
    }

    @Test
    public void testAggregatorWithNoMatchingPages() {
        Object object = new Object();
        List<PageDescription> pageDescriptions = new ArrayList<>();

        FormDescription formDescription = this.createForm(object, 3, 0);
        FormDescription formDescription2 = this.createForm(object, 3, 0);

        pageDescriptions.addAll(formDescription.getPageDescriptions());
        pageDescriptions.addAll(formDescription2.getPageDescriptions());

        FormDescriptionAggregator aggregator = new FormDescriptionAggregator();
        Optional<FormDescription> optional = aggregator.aggregate(pageDescriptions, List.of(object), new IObjectService.NoOp());

        assertThat(optional).isEmpty();
    }

    private FormDescription createForm(Object object, int numberOfPages, int numberOfCanCreate) {
        List<PageDescription> pageDescriptions = new ArrayList<>();
        int initialCount = numberOfCanCreate;

        for (int i = 0; i < numberOfPages; i++) {
            boolean canCreate = 0 < initialCount--;
            GroupDescription groupDescription = this.createGroup();
            PageDescription pageDescription = this.createPage(object, groupDescription, canCreate);
            pageDescriptions.add(pageDescription);
        }

        // @formatter:off
        return FormDescription.newFormDescription(UUID.randomUUID().toString())
                .targetObjectIdProvider(targetObjectIdProvider -> "targetObjectId")
                .canCreatePredicate(variableManager -> true)
                .pageDescriptions(pageDescriptions)
                .idProvider(variableManager -> UUID.randomUUID().toString())
                .labelProvider(variableManager -> LABEL)
                .label(LABEL)
                .build();
        // @formatter:on
    }

    private PageDescription createPage(Object object, GroupDescription groupDescription, boolean canCreate) {
        // @formatter:off
        return PageDescription.newPageDescription("test")
                .idProvider(variableManager -> "id")
                .canCreatePredicate(variableManager -> canCreate)
                .labelProvider(variableManager ->  "label")
                .semanticElementsProvider(variableManager ->  Collections.singletonList(object))
                .groupDescriptions(Collections.singletonList(groupDescription))
                .build();
        // @formatter:on
    }

    private GroupDescription createGroup() {
        // @formatter:off
        return GroupDescription.newGroupDescription("Group")
                .idProvider(variableManager -> "id")
                .controlDescriptions(Collections.emptyList())
                .labelProvider(variableManager -> LABEL)
                .semanticElementsProvider(variableManager -> Collections.emptyList())
                .build();
        // @formatter:on
    }
}
