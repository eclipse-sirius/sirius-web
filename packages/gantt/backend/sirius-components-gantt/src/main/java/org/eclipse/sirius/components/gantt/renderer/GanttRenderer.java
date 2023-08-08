/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.gantt.renderer;

import java.util.Optional;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.representations.BaseRenderer;
import org.eclipse.sirius.components.representations.Element;

/**
 * Renderer used to create gantt representations.
 *
 * @author lfasani
 */
public class GanttRenderer {

    private final BaseRenderer baseRenderer;

    public GanttRenderer() {
        this.baseRenderer = new BaseRenderer(new GanttInstancePropsValidator(), new GanttComponentPropsValidator(), new GanttElementFactory());
    }

    public Gantt render(Element element) {
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Gantt.class::isInstance)
                .map(Gantt.class::cast)
                .orElse(null);
    }
}
