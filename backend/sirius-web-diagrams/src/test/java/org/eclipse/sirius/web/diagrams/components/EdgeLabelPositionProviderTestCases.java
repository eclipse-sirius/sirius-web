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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.TextBounds;
import org.eclipse.sirius.web.diagrams.TextBoundsProvider;
import org.junit.Test;

/**
 * Test cases for {@link EdgeLabelPositionProvider}.
 *
 * @author wpiers
 */
public class EdgeLabelPositionProviderTestCases {

    private static final int FONT_SIZE = 16;

    private static final String LABEL_COLOR = "#000000"; //$NON-NLS-1$

    private static final String ICON_URL = ""; //$NON-NLS-1$

    private static final String TEST_LABEL = "My Label"; //$NON-NLS-1$

    private List<Position> routingPoints = Arrays.asList(Position.at(0, 0), Position.at(100, 100));

    @Test
    public void testEdgeLabelBoundsPosition() {
        EdgeLabelPositionProvider labelBoundsProvider = new EdgeLabelPositionProvider(this.routingPoints);
        LabelStyle labelStyle = LabelStyle.newLabelStyle().color(LABEL_COLOR).fontSize(FONT_SIZE).iconURL(ICON_URL).build();
        TextBounds textBounds = new TextBoundsProvider().computeBounds(labelStyle, TEST_LABEL);

        Position position = labelBoundsProvider.getPosition(Optional.empty(), textBounds, LabelType.EDGE_CENTER.getValue());
        assertThat(position).extracting(Position::getX).isEqualTo(Double.valueOf(17.5390625));
        assertThat(position).extracting(Position::getY).isEqualTo(Double.valueOf(50));
    }

}
