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
package org.eclipse.sirius.web.diagrams.renderer;

import java.util.Optional;

import org.eclipse.sirius.web.components.BaseRenderer;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.slf4j.Logger;

/**
 * Renderer used to create the diagram from its description and some variables.
 * <p>
 * It will delegate most of its behavior to the abstract renderer which will process the tree of elements to render. The
 * diagram renderer will mostly be used in order to let the abstract renderer delegate some diagram-specific behavior
 * such as the instantiation of the diagram concrete types and the validation of the properties of both the diagram
 * elements and the diagram components.
 * </p>
 *
 * @author sbegaudeau
 */
public class DiagramRenderer {

    private final BaseRenderer baseRenderer;

    public DiagramRenderer(Logger logger) {
        this.baseRenderer = new BaseRenderer(new DiagramInstancePropsValidator(), new DiagramComponentPropsValidator(), new DiagramElementFactory(logger), logger);
    }

    public Diagram render(Element element) {
        // @formatter:off
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast)
                .orElse(null);
        // @formatter:on
    }

}
