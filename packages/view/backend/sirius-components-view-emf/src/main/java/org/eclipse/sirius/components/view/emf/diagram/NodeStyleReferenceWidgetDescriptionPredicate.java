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
package org.eclipse.sirius.components.view.emf.diagram;

import org.eclipse.sirius.components.view.emf.diagram.api.INodeStyleReferenceWidgetDescriptionPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to test if a reference widget description belongs to custom node style properties.
 *
 * @author tgiraudet
 */
@Service
public class NodeStyleReferenceWidgetDescriptionPredicate implements INodeStyleReferenceWidgetDescriptionPredicate {

    @Override
    @SuppressWarnings("checkstyle:BooleanExpressionComplexity")
    public boolean test(String referenceWidgetDescriptionId) {
        return NodeStylePropertiesConfigurer.GROWABLE_NODES_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || NodeStylePropertiesConfigurer.WEST_BORDER_NODES_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || NodeStylePropertiesConfigurer.EAST_BORDER_NODES_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || NodeStylePropertiesConfigurer.SOUTH_BORDER_NODES_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || NodeStylePropertiesConfigurer.NORTH_BORDER_NODES_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || NodeStylePropertiesConfigurer.RECTANGULAR_BACKGROUND_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || NodeStylePropertiesConfigurer.ICON_BACKGROUND_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || NodeStylePropertiesConfigurer.BORDER_COLOR_DESCRIPTION_ID.equals(referenceWidgetDescriptionId);
    }
}

