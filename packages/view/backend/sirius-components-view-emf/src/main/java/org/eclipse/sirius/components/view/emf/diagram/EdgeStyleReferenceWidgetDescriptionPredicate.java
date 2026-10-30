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

import org.eclipse.sirius.components.view.emf.diagram.api.IEdgeStyleReferenceWidgetDescriptionPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to test if a reference widget description belongs to custom edge style properties.
 *
 * @author tgiraudet
 */
@Service
public class EdgeStyleReferenceWidgetDescriptionPredicate implements IEdgeStyleReferenceWidgetDescriptionPredicate {

    @Override
    public boolean test(String referenceWidgetDescriptionId) {
        return EdgeStylePropertiesConfigurer.COLOR_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || EdgeStylePropertiesConfigurer.BACKGROUND_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || EdgeStylePropertiesConfigurer.BORDER_COLOR_DESCRIPTION_ID.equals(referenceWidgetDescriptionId);
    }
}

