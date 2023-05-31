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
package org.eclipse.sirius.components.forms.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.forms.elements.TreeElementProps;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the tree widget and its nodes.
 *
 * @author pcdavid
 */
public class TreeComponent implements IComponent {

    public static final String ROOT_VARIABLE = "root";

    public static final String ANCESTORS_VARIABLE = "ancestors";

    public static final String NODES_VARIABLE = "nodes";

    private final TreeComponentProps props;

    public TreeComponent(TreeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        TreeDescription treeDescription = this.props.getTreeDescription();

        String id = treeDescription.getIdProvider().apply(variableManager);
        String label = treeDescription.getLabelProvider().apply(variableManager);
        String iconURL = treeDescription.getIconURLProvider().apply(variableManager);
        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(treeDescription, variableManager)));

        // Compute the recursive structure of semantic elements
        variableManager.put(ROOT_VARIABLE, variableManager.get(VariableManager.SELF, Object.class).orElse(null));

        List<TreeNode> nodes = this.computeChildren(List.of(), variableManager, treeDescription);

        VariableManager expansionVariableManager = variableManager.createChild();
        expansionVariableManager.put(NODES_VARIABLE, nodes);
        List<String> expandedIds = treeDescription.getExpandedNodeIdsProvider().apply(expansionVariableManager);

        TreeElementProps.Builder treeElementPropsBuilder = TreeElementProps.newTreeElementProps(id)
                .label(label)
                .iconURL(iconURL)
                .children(children)
                .nodes(nodes)
                .expandedNodesIds(expandedIds);

        if (treeDescription.getHelpTextProvider() != null) {
            treeElementPropsBuilder.helpTextProvider(() -> treeDescription.getHelpTextProvider().apply(variableManager));
        }

        return new Element(TreeElementProps.TYPE, treeElementPropsBuilder.build());
    }

    private List<TreeNode> computeChildren(List<Object> ancestors, VariableManager variableManager, TreeDescription treeDescription) {
        List<TreeNode> nodes = new ArrayList<>();

        VariableManager itemVariableManager = variableManager.createChild();
        itemVariableManager.put(ANCESTORS_VARIABLE, ancestors);

        String parentId = null;
        if (!ancestors.isEmpty()) {
            itemVariableManager.put(VariableManager.SELF, ancestors.get(ancestors.size() - 1));
            parentId = treeDescription.getNodeIdProvider().apply(itemVariableManager);
        }

        List<?> semanticChildren = treeDescription.getChildrenProvider().apply(itemVariableManager);

        for (Object child : semanticChildren) {
            VariableManager childVariableManager = new VariableManager();
            itemVariableManager.getVariables().forEach(childVariableManager::put);
            childVariableManager.put(VariableManager.SELF, child);

            TreeNode node = this.renderNode(childVariableManager, treeDescription, parentId);
            nodes.add(node);

            List<Object> newAncestors = new ArrayList<>(ancestors);
            newAncestors.add(child);
            nodes.addAll(this.computeChildren(newAncestors, variableManager, treeDescription));
        }

        return nodes;
    }

    private TreeNode renderNode(VariableManager variableManager, TreeDescription treeDescription, String parentId) {
        String nodeId = treeDescription.getNodeIdProvider().apply(variableManager);
        String nodeLabel = treeDescription.getNodeLabelProvider().apply(variableManager);
        String nodeKind = treeDescription.getNodeKindProvider().apply(variableManager);
        String nodeImageURL = treeDescription.getNodeImageURLProvider().apply(variableManager);
        boolean nodeSelectable = treeDescription.getNodeSelectableProvider().apply(variableManager);
        // @formatter:off
        return TreeNode.newTreeNode(nodeId)
                .parentId(parentId)
                .label(nodeLabel)
                .kind(nodeKind)
                .imageURL(nodeImageURL)
                .selectable(nodeSelectable)
                .build();
        // @formatter:on
    }
}
