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

import org.assertj.core.api.Assert;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.InstanceOfAssertFactory;
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
 * Static diagram {@link InstanceOfAssertFactory InstanceOfAssertFactories} for {@link Assert#asInstanceOf(InstanceOfAssertFactory)}.
 *
 * @author gdaniel
 */
public interface DiagramInstanceOfAssertFactories extends InstanceOfAssertFactories {

    InstanceOfAssertFactory<Diagram, DiagramAssert> DIAGRAM = new InstanceOfAssertFactory<>(Diagram.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<Edge, EdgeAssert> EDGE = new InstanceOfAssertFactory<>(Edge.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<EdgeStyle, EdgeStyleAssert> EDGE_STYLE = new InstanceOfAssertFactory<>(EdgeStyle.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<FreeFormLayoutStrategy, FreeFormLayoutStrategyAssert> FREE_FORM_LAYOUT_STRATEGY = new InstanceOfAssertFactory<>(FreeFormLayoutStrategy.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<IconLabelNodeStyle, IconLabelNodeStyleAssert> ICON_LABEL_NODE_STYLE = new InstanceOfAssertFactory<>(IconLabelNodeStyle.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<ImageNodeStyle, ImageNodeStyleAssert> IMAGE_NODE_STYLE = new InstanceOfAssertFactory<>(ImageNodeStyle.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<InsideLabel, InsideLabelAssert> INSIDE_LABEL = new InstanceOfAssertFactory<>(InsideLabel.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<Label, LabelAssert> LABEL = new InstanceOfAssertFactory<>(Label.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<LabelStyle, LabelStyleAssert> LABEL_STYLE = new InstanceOfAssertFactory<>(LabelStyle.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<ListLayoutStrategy, ListLayoutStrategyAssert> LIST_LAYOUT_STRATEGY = new InstanceOfAssertFactory<>(ListLayoutStrategy.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<Node, NodeAssert> NODE = new InstanceOfAssertFactory<>(Node.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<OutsideLabel, OutsideLabelAssert> OUTSIDE_LABEL = new InstanceOfAssertFactory<>(OutsideLabel.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<ParametricSVGNodeStyle, ParametricSVGNodeStyleAssert> PARAMETRIC_SVG_NODE_STYLE = new InstanceOfAssertFactory<>(ParametricSVGNodeStyle.class, DiagramAssertions::assertThat);

    InstanceOfAssertFactory<RectangularNodeStyle, RectangularNodeStyleAssert> RECTANGULAR_NODE_STYLE = new InstanceOfAssertFactory<>(RectangularNodeStyle.class, DiagramAssertions::assertThat);
}
