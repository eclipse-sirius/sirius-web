/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.charts.hierarchy.renderer;

import java.util.Optional;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.representations.BaseRenderer;
import org.eclipse.sirius.components.representations.Element;

/**
 * Renderer used to create hierarchy representations.
 *
 * @author sbegaudeau
 */
public class HierarchyRenderer {

    private final BaseRenderer baseRenderer;

    public HierarchyRenderer() {
        this.baseRenderer = new BaseRenderer(new HierarchyInstancePropsValidator(), new HierarchyComponentPropsValidator(), new HierarchyElementFactory());
    }

    public Hierarchy render(Element element) {
        // @formatter:off
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Hierarchy.class::isInstance)
                .map(Hierarchy.class::cast)
                .orElse(null);
        // @formatter:on
    }
}
