/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.assertions;

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.OutsideLabel;
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;

/**
 * Provides AssertJ assertions for diagram in addition to existing ones.
 *
 * @author gdaniel
 */
public class DiagramAssertions extends Assertions implements DiagramInstanceOfAssertFactories {

    public static DiagramAssert assertThat(Diagram diagram) {
        return new DiagramAssert(diagram);
    }

    public static EdgeAssert assertThat(Edge edge) {
        return new EdgeAssert(edge);
    }

    public static EdgeStyleAssert assertThat(EdgeStyle edgeStyle) {
        return new EdgeStyleAssert(edgeStyle);
    }

    public static FreeFormLayoutStrategyAssert assertThat(FreeFormLayoutStrategy freeFormLayoutStrategy) {
        return new FreeFormLayoutStrategyAssert(freeFormLayoutStrategy);
    }

    public static IconLabelNodeStyleAssert assertThat(IconLabelNodeStyle iconLabelNodeStyle) {
        return new IconLabelNodeStyleAssert(iconLabelNodeStyle);
    }

    public static ImageNodeStyleAssert assertThat(ImageNodeStyle imageNodeStyle) {
        return new ImageNodeStyleAssert(imageNodeStyle);
    }

    public static InsideLabelAssert assertThat(InsideLabel insideLabel) {
        return new InsideLabelAssert(insideLabel);
    }

    public static LabelAssert assertThat(Label label) {
        return new LabelAssert(label);
    }

    public static LabelStyleAssert assertThat(LabelStyle labelStyle) {
        return new LabelStyleAssert(labelStyle);
    }

    public static ListLayoutStrategyAssert assertThat(ListLayoutStrategy listLayoutStrategy) {
        return new ListLayoutStrategyAssert(listLayoutStrategy);
    }

    public static NodeAssert assertThat(Node node) {
        return new NodeAssert(node);
    }

    public static OutsideLabelAssert assertThat(OutsideLabel outsideLabel) {
        return new OutsideLabelAssert(outsideLabel);
    }

    public static ParametricSVGNodeStyleAssert assertThat(ParametricSVGNodeStyle parametricSvgNodeStyle) {
        return new ParametricSVGNodeStyleAssert(parametricSvgNodeStyle);
    }

    public static RectangularNodeStyleAssert assertThat(RectangularNodeStyle rectangularNodeStyle) {
        return new RectangularNodeStyleAssert(rectangularNodeStyle);
    }

}
