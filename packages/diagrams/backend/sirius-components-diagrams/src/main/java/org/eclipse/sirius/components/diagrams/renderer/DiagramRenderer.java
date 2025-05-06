/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.renderer;

import java.util.Optional;

import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.representations.BaseRenderer;
import org.eclipse.sirius.components.representations.Element;

/**
 * Renderer used to create the diagram from its description and some variables.
 *
 * <p>
 *     It will delegate most of its behavior to the abstract renderer which will process the tree of elements to render.
 *     The diagram renderer will mostly be used in order to let the abstract renderer delegate some diagram-specific
 *     behavior such as the instantiation of the diagram concrete types and the validation of the properties of both the
 *     diagram elements and the diagram components.
 * </p>
 *
 * <p>
 *     The behavior and lifecycle of the diagram renderer can be considered using a comparison with some pseudo-code using
 *     the JavaScript framework React.
 * </p>
 *
 * {@snippet id="diagram-renderer-in-react" lang="typescript":
 * const element = &lt;DiagramComponent
 *                        variableManager={variableManager}
 *                        diagramDescription={diagramDescription}
 *                        ... /&gt;
 * const diagram = new DiagramRenderer().render(element);
 * }
 *
 * <p>
 *     The only relevant element that the diagram renderer can accept is a {@link org.eclipse.sirius.components.diagrams.components.DiagramComponent diagram component}.
 * </p>
 *
 * {@snippet id="diagram-renderer" lang="java":
 * var props = DiagramComponentProps.newDiagramComponentProps()
 *                           .variableManager(variableManager)
 *                           .diagramDescription(diagramDescription)
 *                           .build();
 * var element = new Element(DiagramComponent.class, props);
 * Diagram diagram = new DiagramRenderer().render(element);
 * }
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@PublicApi
public class DiagramRenderer {

    private final BaseRenderer baseRenderer;

    public DiagramRenderer() {
        this.baseRenderer = new BaseRenderer(new DiagramInstancePropsValidator(), new DiagramComponentPropsValidator(), new DiagramElementFactory());
    }

    public Diagram render(Element element) {
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast)
                .orElse(null);
    }

}
