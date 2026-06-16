/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.studio.services;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.ILayoutGroupsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutGroup;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Component;

/**
 * Layout provider that puts all diagram nodes into a single layout group
 * with the default layered configuration.
 *
 * @author ocailleau
 */
@Component
public class DefaultLayoutGroupsProvider implements ILayoutGroupsProvider {

    private final DefaultLayoutConfigurationProvider defaultLayoutConfigProvider;

    public DefaultLayoutGroupsProvider(DefaultLayoutConfigurationProvider defaultLayoutConfigProvider) {
        this.defaultLayoutConfigProvider = Objects.requireNonNull(defaultLayoutConfigProvider);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription) {
        return diagramContext != null && diagramContext.diagram() != null && diagramDescription != null;
    }

    @Override
    public List<LayoutGroup> getLayoutGroups(IEditingContext context, DiagramContext diagramContext, DiagramDescription description) {
        Diagram diagram = diagramContext.diagram();
        if (diagram == null || diagram.getNodes() == null || diagram.getNodes().isEmpty()) {
            return List.of();
        }

        List<LayoutConfiguration> allConfigs = this.defaultLayoutConfigProvider.getLayoutConfiguration(context, diagramContext, description);

        LayoutConfiguration layeredConfig = allConfigs.stream()
                .filter(config -> "elk-layered".equals(config.id()))
                .findFirst()
                .orElse(null);

        List<String> allNodeIds = new ArrayList<>();
        for (Node rootNode : diagram.getNodes()) {
            this.collectNodeIdsRecursively(rootNode, allNodeIds);
        }

        LayoutGroup singleGlobalGroup = LayoutGroup.newLayoutGroup("group-1")
                .nodeIds(allNodeIds)
                .layoutConfiguration(layeredConfig)
                .build();

        return List.of(singleGlobalGroup);
    }
    private void collectNodeIdsRecursively(Node node, List<String> accumulator) {
        if (node == null) {
            return;
        }

        accumulator.add(node.getId());

        if (node.getBorderNodes() != null) {
            for (Node borderNode : node.getBorderNodes()) {
                this.collectNodeIdsRecursively(borderNode, accumulator);
            }
        }

        if (node.getChildNodes() != null) {
            for (Node childNode : node.getChildNodes()) {
                this.collectNodeIdsRecursively(childNode, accumulator);
            }
        }
    }
}