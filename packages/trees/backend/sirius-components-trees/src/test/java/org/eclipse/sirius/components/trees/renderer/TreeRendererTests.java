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
package org.eclipse.sirius.components.trees.renderer;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link TreeRenderer}.
 *
 * @author Arthur Daussy
 */
public class TreeRendererTests {

    private static final String ITEM_RENDERING_LOOP_LABEL = "<Item rendering loop> root";

    private static final String CHILD1X1_ID = "child1x1";

    private static final String CHILD2_ID = "child2";

    private static final String CHILD1_ID = "child1";

    private static final String ROOT_LABEL = "root";

    private static final String NODE_KIND = "node";

    private static final String FAKE_ID = "FakeId";

    @Test
    public void basicRenderingOneRoot() {

        TreeNode root = new TreeNode(ROOT_LABEL);
        TreeDescription description = this.createDescription(root);
        VariableManager variableManager = new VariableManager();
        Tree tree = new TreeRenderer(variableManager, description).render();

        assertEquals(FAKE_ID, tree.getId());
        assertEquals(1, tree.getChildren().size());
        this.assertTreeNode(tree.getChildren().get(0), ROOT_LABEL, 0);

    }

    @Test
    public void basicRenderingSmallTree() {

        TreeNode root = new TreeNode(ROOT_LABEL);

        TreeNode child1 = root.createContainementChildren(CHILD1_ID);
        TreeNode child2 = root.createContainementChildren(CHILD2_ID);

        TreeNode child1x1 = child1.createContainementChildren(CHILD1X1_ID);

        child2.addReferenceChildren(child1x1);

        TreeDescription description = this.createDescription(root);
        VariableManager variableManager = new VariableManager();
        Tree tree = new TreeRenderer(variableManager, description).render();

        assertEquals(FAKE_ID, tree.getId());
        assertEquals(1, tree.getChildren().size());
        TreeItem rootTreeItem = tree.getChildren().get(0);
        this.assertTreeNode(rootTreeItem, ROOT_LABEL, 2);

        TreeItem child1TreeItem = rootTreeItem.getChildren().get(0);
        this.assertTreeNode(child1TreeItem, CHILD1_ID, 1);

        TreeItem child1x1TreeItem = child1TreeItem.getChildren().get(0);
        this.assertTreeNode(child1x1TreeItem, CHILD1X1_ID, 0);

        TreeItem child2TreeItem = rootTreeItem.getChildren().get(1);
        this.assertTreeNode(child2TreeItem, CHILD2_ID, 1);

        TreeItem refChild1x1TreeItem = child2TreeItem.getChildren().get(0);
        this.assertTreeNode(refChild1x1TreeItem, CHILD1X1_ID, 0);

    }

    /**
     * This test aims to check that the TreeRendering do not throw a StackOverflow exception when a tree to be rendered
     * has loops. It also verify that the virtual nodes used to report a problem to the user all have unique ids.
     */
    @Test
    public void checkAvoidStackOverFlow() {
        TreeNode root = new TreeNode(ROOT_LABEL);

        TreeNode child1 = root.createContainementChildren(CHILD1_ID);
        TreeNode child2 = root.createContainementChildren(CHILD2_ID);

        child1.addReferenceChildren(root);
        child1.addReferenceChildren(root);
        child2.addReferenceChildren(root);

        TreeDescription description = this.createDescription(root);
        VariableManager variableManager = new VariableManager();
        Tree tree = new TreeRenderer(variableManager, description).render();

        assertEquals(FAKE_ID, tree.getId());
        assertEquals(1, tree.getChildren().size());
        TreeItem rootTreeItem = tree.getChildren().get(0);
        this.assertTreeNode(rootTreeItem, ROOT_LABEL, 2);

        TreeItem child1TreeItem = rootTreeItem.getChildren().get(0);
        this.assertTreeNode(child1TreeItem, CHILD1_ID, 2);

        TreeItem refRoot1x1 = child1TreeItem.getChildren().get(0);
        this.assertTreeNode(refRoot1x1, ITEM_RENDERING_LOOP_LABEL, 0);
        TreeItem refRoot1x2 = child1TreeItem.getChildren().get(1);
        this.assertTreeNode(refRoot1x2, ITEM_RENDERING_LOOP_LABEL, 0);

        TreeItem child2TreeItem = rootTreeItem.getChildren().get(1);
        this.assertTreeNode(child2TreeItem, CHILD2_ID, 1);

        TreeItem refRoot2 = child2TreeItem.getChildren().get(0);
        this.assertTreeNode(refRoot2, ITEM_RENDERING_LOOP_LABEL, 0);

        // All ids are unique
        assertEquals(3, Set.of(refRoot1x1.getId(), refRoot1x2.getId(), refRoot2.getId()).size());

    }

    private TreeDescription createDescription(TreeNode root) {
        return TreeDescription.newTreeDescription(FAKE_ID)
                .canCreatePredicate(v -> true)
                .childrenProvider(v -> this.getSelfNode(v).getChildren())
                .deletableProvider(v -> false)
                .deleteHandler(v -> new Success())
                .editableProvider(v -> true).elementsProvider(v -> List.of(root))
                .hasChildrenProvider(v -> !this.getSelfNode(v).getChildren().isEmpty())
                .iconURLProvider(v -> List.of())
                .idProvider(v -> FAKE_ID)
                .kindProvider(v -> NODE_KIND)
                .label("Fake tree")
                .labelProvider(v -> StyledString.of(v.get(VariableManager.SELF, TreeNode.class).map(TreeNode::getId).orElse(FAKE_ID)))
                .parentObjectProvider(v -> this.getSelfNode(v).getParent())
                .renameHandler((v, name) -> new Success())
                .selectableProvider(v -> true)
                .targetObjectIdProvider(v -> v.get(VariableManager.SELF, TreeNode.class).map(TreeNode::getId).orElse(FAKE_ID))
                .treeItemIdProvider(v -> this.getSelfNode(v).getId())
                .treeItemObjectProvider(v -> root.search(v.get("treeItemId", String.class).get(), new HashSet<>()))
                .treeItemLabelProvider(v -> StyledString.of(v.get(VariableManager.SELF, TreeNode.class).map(TreeNode::getId).orElse(FAKE_ID)))
                .contextMenuEntries(List.of())
                .build();

    }

    private void assertTreeNode(TreeItem treeItem, String expectedSimpleLabel, int exepectedNbChildren) {
        assertEquals(expectedSimpleLabel, treeItem.getLabel().styledStringFragments().stream().map(StyledStringFragment::text).collect(joining()));
        assertEquals(NODE_KIND, treeItem.getKind());
        assertEquals(exepectedNbChildren, treeItem.getChildren().size());
    }

    private TreeNode getSelfNode(VariableManager mng) {
        return mng.get(VariableManager.SELF, TreeNode.class).get();
    }

    /**
     * Item used to create simple tree.
     *
     * @author Arthur Daussy
     */
    private static final class TreeNode {

        private final String id;

        private final List<TreeNode> children = new ArrayList<>();

        private TreeNode parent;

        private TreeNode(String id) {
            super();
            this.id = id;
        }

        public TreeNode getParent() {
            return this.parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode createContainementChildren(String childId) {
            TreeNode child = new TreeNode(childId);
            this.children.add(child);
            child.setParent(this);
            return child;
        }

        public void addReferenceChildren(TreeNode node) {
            this.children.add(node);
        }

        public String getId() {
            return this.id;
        }

        public List<TreeNode> getChildren() {
            return this.children;
        }

        public Optional<TreeNode> search(String searchId, Set<String> searchNodeId) {
            Optional<TreeNode> result = Optional.empty();
            if (searchNodeId.contains(this.id)) {
                result = Optional.empty();
            } else if (this.id.equals(searchId)) {
                result = Optional.of(this);
            } else {
                searchNodeId.add(this.id);

                for (TreeNode child : this.children) {
                    result = child.search(searchId, searchNodeId);
                    if (result.isPresent()) {
                        return result;
                    }
                }

            }

            return result;
        }

    }

}
