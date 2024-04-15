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
package org.eclipse.sirius.components.collaborative.widget.reference.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.components.collaborative.trees.api.TreeConfiguration;
import org.eclipse.sirius.components.collaborative.widget.reference.browser.ModelBrowsersDescriptionProvider;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.EMFKindService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the model browser description.
 *
 * @author pcdavid
 */
public class ModelBrowserTests {

    @Test
    public void testModelBrowserDescriptionProvider() {
        IEditingContext editingContext = () -> "editingContextId";

        var urlParser = new URLParser();
        var provider = new ModelBrowsersDescriptionProvider(new IObjectService.NoOp(), urlParser, new EMFKindService(urlParser), List.of());
        var descriptions = provider.getRepresentationDescriptions(editingContext);
        assertThat(descriptions).hasSize(2);
        var treeDescriptions = descriptions.stream().filter(TreeDescription.class::isInstance).map(TreeDescription.class::cast).toList();
        assertThat(treeDescriptions).hasSize(2);

        var containerBrowserDescription = treeDescriptions.stream().filter(treeDescription -> treeDescription.getId().equals(ModelBrowsersDescriptionProvider.CONTAINER_DESCRIPTION_ID)).findFirst();
        assertThat(containerBrowserDescription).isPresent();
        var referenceBrowserDescription = treeDescriptions.stream().filter(treeDescription -> treeDescription.getId().equals(ModelBrowsersDescriptionProvider.REFERENCE_DESCRIPTION_ID)).findFirst();
        assertThat(referenceBrowserDescription).isPresent();

        var variableManager = new VariableManager();
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

        var referenceTreeId = "modelBrowser://reference";
        variableManager.put(TreeConfiguration.TREE_ID, referenceTreeId);
        var tree = new TreeRenderer(variableManager, referenceBrowserDescription.get()).render();
        assertThat(tree).isNotNull();
        assertThat(tree.getId()).isEqualTo(referenceTreeId);
        assertThat(tree.getDescriptionId()).isEqualTo(ModelBrowsersDescriptionProvider.REFERENCE_DESCRIPTION_ID);
        assertThat(tree.getKind()).isEqualTo(Tree.KIND);

        var containerTreeId = "modelBrowser://container";
        variableManager.put(TreeConfiguration.TREE_ID, containerTreeId);
        tree = new TreeRenderer(variableManager, containerBrowserDescription.get()).render();
        assertThat(tree).isNotNull();
        assertThat(tree.getId()).isEqualTo(containerTreeId);
        assertThat(tree.getDescriptionId()).isEqualTo(ModelBrowsersDescriptionProvider.CONTAINER_DESCRIPTION_ID);
        assertThat(tree.getKind()).isEqualTo(Tree.KIND);

    }
}
