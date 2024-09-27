/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.architecture.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.handlers.DefaultExpandAllTreePathHandler;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeNavigationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.junit.jupiter.api.Test;

/**
 * Test for the DefaultExpandAllTreePathHandler.
 *
 * @author frouene
 */
public class DefaultExpandAllTreePathHandlerTests {

    @Test
    public void testMaximumDepthReached() {
        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String id) {
                TreeDescription diagramDescription = TreeDescription.newTreeDescription("descriptionId")
                        .canCreatePredicate(v -> true)
                        .childrenProvider(v -> List.of(new Object()))
                        .deletableProvider(v -> false)
                        .deleteHandler(v -> new Success())
                        .editableProvider(v -> true)
                        .elementsProvider(v -> List.of())
                        .hasChildrenProvider(v -> true)
                        .iconURLProvider(v -> List.of())
                        .idProvider(v -> "id")
                        .kindProvider(v -> "kind")
                        .label("Fake tree")
                        .labelProvider(v -> null)
                        .parentObjectProvider(v -> null)
                        .renameHandler((v, name) -> new Success())
                        .selectableProvider(v -> true)
                        .targetObjectIdProvider(v -> null)
                        .treeItemIdProvider(v -> "itemId")
                        .treeItemObjectProvider(v -> new Object())
                        .treeItemLabelProvider(v -> null)
                        .contextMenuEntries(List.of())
                        .build();

                return Optional.of(diagramDescription);
            }
        };

        var handler = new DefaultExpandAllTreePathHandler(new ITreeNavigationService.NoOp(), representationDescriptionSearchService);
        Tree tree = Tree.newTree("treeId")
                .targetObjectId("targetObjectId")
                .descriptionId("descriptionId")
                .label("label")
                .children(List.of())
                .build();

        var input = new ExpandAllTreePathInput(UUID.randomUUID(), "editingContextId", "representationId", "treeItemId");

        var result = handler.handle(new IEditingContext.NoOp(), tree, input);
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(ExpandAllTreePathSuccessPayload.class);
        assertThat(((ExpandAllTreePathSuccessPayload) result).treePath().getMaxDepth()).isEqualTo(100);

    }
}
