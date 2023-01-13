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
package org.eclipse.sirius.components.diagrams.layout.incremental.provider;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Provides the size of a node label.
 *
 * @author gcoutable
 */
public class NodeLabelSizeProvider {

    public ElkPadding getLabelPadding(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        ElkPadding padding = null;

        IPropertyHolder iconLabelProperties = layoutConfigurator.configureByType(node.getNodeType());
        if (iconLabelProperties.hasProperty(CoreOptions.SPACING_INDIVIDUAL)) {
            IPropertyHolder individualSpacings = iconLabelProperties.getProperty(CoreOptions.SPACING_INDIVIDUAL);
            if (individualSpacings.hasProperty(CoreOptions.NODE_LABELS_PADDING)) {
                padding = individualSpacings.getProperty(CoreOptions.NODE_LABELS_PADDING);
            }
        }

        if (padding == null && node.getParent() instanceof NodeLayoutData) {
            NodeLayoutData parent = (NodeLayoutData) node.getParent();
            padding = layoutConfigurator.configureByType(parent.getNodeType()).getProperty(CoreOptions.NODE_LABELS_PADDING);
        }

        if (padding == null) {
            padding = CoreOptions.NODE_LABELS_PADDING.getDefault();
        }

        return padding;
    }

}
