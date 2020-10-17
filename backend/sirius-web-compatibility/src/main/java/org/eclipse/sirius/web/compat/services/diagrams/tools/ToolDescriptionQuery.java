/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.services.diagrams.tools;

import java.util.List;

import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.tool.ContainerCreationDescription;
import org.eclipse.sirius.diagram.description.tool.EdgeCreationDescription;
import org.eclipse.sirius.diagram.description.tool.NodeCreationDescription;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;

/**
 * A class aggregating all the queries (read-only!) having an {@link AbstractToolDescription} as a starting point.
 *
 * @author hmarchadour
 */
public class ToolDescriptionQuery {

    private final AbstractToolDescription siriusTool;

    public ToolDescriptionQuery(AbstractToolDescription siriusTool) {
        this.siriusTool = siriusTool;
    }

    public boolean isHandled() {
        return this.siriusTool instanceof NodeCreationDescription || this.siriusTool instanceof ContainerCreationDescription
                || this.siriusTool instanceof org.eclipse.sirius.viewpoint.description.tool.ToolDescription || this.siriusTool instanceof EdgeCreationDescription;
    }

    public boolean canApplyOn(DiagramDescription diagramDescription) {
        if (this.siriusTool instanceof EdgeCreationDescription) {
            return false;
        } else {
            return new CanCreateMappingTester(this.getMappingsToCreate()).canCreateIn(diagramDescription);
        }
    }

    public boolean canApplyOn(ContainerMapping containerMapping) {
        if (this.siriusTool instanceof EdgeCreationDescription) {
            EdgeCreationDescription edgeCreationDescription = (EdgeCreationDescription) this.siriusTool;
            boolean result = false;
            for (EdgeMapping edge : edgeCreationDescription.getEdgeMappings()) {
                if (edge.getSourceMapping().contains(containerMapping)) {
                    result = true;
                    break;
                }
            }
            return result;
        } else {
            return new CanCreateMappingTester(this.getMappingsToCreate()).canCreateIn(containerMapping);
        }
    }

    public boolean canApplyOn(NodeMapping nodeMapping) {
        if (this.siriusTool instanceof EdgeCreationDescription) {
            EdgeCreationDescription edgeCreationDescription = (EdgeCreationDescription) this.siriusTool;
            boolean result = false;
            for (EdgeMapping edge : edgeCreationDescription.getEdgeMappings()) {
                if (edge.getSourceMapping().contains(nodeMapping)) {
                    result = true;
                    break;
                }
            }
            return result;
        } else {
            return new CanCreateMappingTester(this.getMappingsToCreate()).canCreateIn(nodeMapping);
        }
    }

    private List<? extends AbstractNodeMapping> getMappingsToCreate() {
        final List<? extends AbstractNodeMapping> result;
        if (this.siriusTool instanceof NodeCreationDescription) {
            result = ((NodeCreationDescription) this.siriusTool).getNodeMappings();
        } else if (this.siriusTool instanceof ContainerCreationDescription) {
            result = ((ContainerCreationDescription) this.siriusTool).getContainerMappings();
        } else {
            result = List.of();
        }
        return result;
    }

}
