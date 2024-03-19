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
package org.eclipse.sirius.components.forms.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.TreeWidget;

/**
 * Custom assertion class used to perform some tests on a tree widget.
 *
 * @author sbegaudeau
 */
public class TreeWidgetAssert extends AbstractAssert<TreeWidgetAssert, TreeWidget> {

    public TreeWidgetAssert(TreeWidget treeWidget) {
        super(treeWidget, TreeWidgetAssert.class);
    }

    public TreeWidgetAssert hasCheckedTreeItemsSize(int size) {
        var checkedTreeNodesCount = this.actual.getNodes().stream()
                .filter(TreeNode::isValue)
                .count();
        assertThat(checkedTreeNodesCount)
                .withFailMessage("Expecting %d checked tree items but found %d instead", size, checkedTreeNodesCount)
                .isEqualTo(size);

        return this;
    }

    public TreeWidgetAssert hasTreeItemWithLabel(String label) {
        var hasTreeItemWithLabel = this.actual.getNodes().stream()
                .anyMatch(treeNode -> treeNode.getLabel().equals(label));
        assertThat(hasTreeItemWithLabel)
                .withFailMessage("Expecting at least one tree item with the label \"" + label + "\" but none has been found")
                .isTrue();

        return this;
    }
}
