/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.components;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.TextBounds;
import org.eclipse.sirius.web.diagrams.TextBoundsProvider;
import org.junit.Test;

/**
 * Test cases for {@link NodeLabelPositionProvider}.
 *
 * @author wpiers
 */
public class NodeLabelPositionProviderTestCases {

    private static final int FONT_SIZE = 16;

    private static final String LABEL_COLOR = "#000000"; //$NON-NLS-1$

    private static final String ICON_URL = ""; //$NON-NLS-1$

    private static final String TEST_LABEL = "My Label"; //$NON-NLS-1$

    private static final Size DEFAULT_NODE_SIZE = Size.of(150, 70);

    @Test
    public void testNodeImageLabelBoundsPosition() {
        NodeLabelPositionProvider labelBoundsProvider = new NodeLabelPositionProvider(NodeType.NODE_IMAGE, DEFAULT_NODE_SIZE);
        LabelStyle labelStyle = LabelStyle.newLabelStyle().color(LABEL_COLOR).fontSize(FONT_SIZE).iconURL(ICON_URL).build();
        TextBounds textBounds = new TextBoundsProvider().computeBounds(labelStyle, TEST_LABEL);

        Position position = labelBoundsProvider.getPosition(Optional.empty(), textBounds, LabelType.INSIDE_CENTER.getValue());
        assertThat(position).extracting(Position::getX).isEqualTo(Double.valueOf(42.5390625));
        assertThat(position).extracting(Position::getY).isEqualTo(Double.valueOf(-23.3984375));
    }

    @Test
    public void testNodeRectangleLabelBoundsPosition() {
        NodeLabelPositionProvider labelBoundsProvider = new NodeLabelPositionProvider(NodeType.NODE_RECTANGLE, DEFAULT_NODE_SIZE);
        LabelStyle labelStyle = LabelStyle.newLabelStyle().color(LABEL_COLOR).fontSize(FONT_SIZE).iconURL(ICON_URL).build();
        TextBounds textBounds = new TextBoundsProvider().computeBounds(labelStyle, TEST_LABEL);

        Position position = labelBoundsProvider.getPosition(Optional.empty(), textBounds, LabelType.INSIDE_CENTER.getValue());
        assertThat(position).extracting(Position::getX).isEqualTo(Double.valueOf(42.5390625));
        assertThat(position).extracting(Position::getY).isEqualTo(Double.valueOf(5));
    }

}
