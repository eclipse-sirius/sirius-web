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
package org.eclipse.sirius.web.diagrams.tests;

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;

/**
 * Entry point of all the AssertJ assertions with the diagram specific ones too.
 *
 * @author sbegaudeau
 */
public class DiagramAssertions extends Assertions {
    public static DiagramAssert assertThat(Diagram diagram) {
        return new DiagramAssert(diagram);
    }

    public static NodeAssert assertThat(Node node) {
        return new NodeAssert(node);
    }

    public static ImageNodeStyleAssert assertThat(ImageNodeStyle imageNodeStyle) {
        return new ImageNodeStyleAssert(imageNodeStyle);
    }

    public static RectangularNodeStyleAssert assertThat(RectangularNodeStyle rectangularNodeStyle) {
        return new RectangularNodeStyleAssert(rectangularNodeStyle);
    }

    public static EdgeAssert assertThat(Edge edge) {
        return new EdgeAssert(edge);
    }

    public static LabelAssert assertThat(Label label) {
        return new LabelAssert(label);
    }

    public static LabelStyleAssert assertThat(LabelStyle labelStyle) {
        return new LabelStyleAssert(labelStyle);
    }
}
