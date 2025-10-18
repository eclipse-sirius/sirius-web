/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.services.browser;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.browser.DefaultModelBrowsersTreeDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Registers a custom model browser tree description based on the default container model browser but with custom labels for testing.
 *
 * @author pcdavid
 */
@Service
public class CustomTreeDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {
    public static final String CUSTOM_PREFIX = "CUSTOM ";

    public static final String CUSTOM_CONTAINER_DESCRIPTION_ID = UUID.nameUUIDFromBytes("custom_model_browser_container_tree_description".getBytes()).toString(); // "6a7d6a4a-4a7c-3063-a4ed-41a8868582cc"

    private final DefaultModelBrowsersTreeDescriptionProvider defaultModelBrowsersTreeDescriptionProvider;

    public CustomTreeDescriptionProvider(DefaultModelBrowsersTreeDescriptionProvider defaultModelBrowsersTreeDescriptionProvider) {
        this.defaultModelBrowsersTreeDescriptionProvider = Objects.requireNonNull(defaultModelBrowsersTreeDescriptionProvider);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        return this.defaultModelBrowsersTreeDescriptionProvider.getRepresentationDescriptions(editingContext).stream()
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast)
                .filter(treeDescription -> treeDescription.getId().equals(DefaultModelBrowsersTreeDescriptionProvider.CONTAINER_DESCRIPTION_ID))
                .map(baseTreeDescription -> {
                    return TreeDescription.newTreeDescription(baseTreeDescription)
                            .id(CUSTOM_CONTAINER_DESCRIPTION_ID)
                            .treeItemLabelProvider((variableManager) -> {
                                var baseLabel = baseTreeDescription.getTreeItemLabelProvider().apply(variableManager);
                                List<StyledStringFragment> fragments = new ArrayList<>();
                                fragments.add(StyledStringFragment.of(CUSTOM_PREFIX));
                                fragments.addAll(baseLabel.styledStringFragments());
                                return new StyledString(fragments);
                            })
                            .build();
                })
                .map(IRepresentationDescription.class::cast)
                .toList();
    }
}
