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
package org.eclipse.sirius.web.diagrams.layout;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Utility class used to compute the size of a piece of text.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class TextBoundsService {
    /**
     * Font used in backend & frontend to compute and draw diagram labels.
     */
    private static final String DEFAULT_LABEL_FONT_NAME = "Arial"; //$NON-NLS-1$

    private static final AffineTransform AFFINE_TRANSFORM = new AffineTransform();

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(AFFINE_TRANSFORM, true, true);

    private static final int SPACE_FOR_ICON = 20;

    private final Logger logger = LoggerFactory.getLogger(TextBoundsService.class);

    private ExecutorService executorService;

    @PostConstruct
    public void initialize() {
        this.logger.debug("AWT initialization starting"); //$NON-NLS-1$

        Runnable computeBounds = () -> {

            // @formatter:off
            LabelStyle labelStyle = LabelStyle.newLabelStyle()
                    .fontSize(16)
                    .color("#000000") //$NON-NLS-1$
                    .iconURL("") //$NON-NLS-1$
                    .build();
            Label label = Label.newLabel("defaultLabelID") //$NON-NLS-1$
                    .type("labelType") //$NON-NLS-1$
                    .position(Position.UNDEFINED)
                    .size(Size.UNDEFINED)
                    .alignment(Position.UNDEFINED)
                    .text("ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz") //$NON-NLS-1$
                    .style(labelStyle)
                    .build();
            this.getBounds(label);
            //@formatter:on
        };

        this.executorService = Executors.newSingleThreadExecutor();
        this.executorService.execute(computeBounds);
        this.executorService.shutdown();

        this.logger.debug("AWT initialization done"); //$NON-NLS-1$
    }

    @PreDestroy
    public void beandestroy() {
        if (this.executorService != null) {
            this.executorService.shutdownNow();
        }
    }

    public TextBounds getBounds(Label label) {
        int fontStyle = Font.PLAIN;
        LabelStyle labelStyle = label.getStyle();
        if (labelStyle.isBold()) {
            fontStyle = fontStyle | Font.BOLD;
        }
        if (labelStyle.isItalic()) {
            fontStyle = fontStyle | Font.ITALIC;
        }
        Font font = new Font(DEFAULT_LABEL_FONT_NAME, fontStyle, label.getStyle().getFontSize());
        Rectangle2D stringBounds = font.getStringBounds(label.getText(), FONT_RENDER_CONTEXT);
        double width = stringBounds.getWidth();
        double height = stringBounds.getHeight();

        double iconWidth = 0;
        double iconHeight = 0;
        if (!label.getStyle().getIconURL().isEmpty()) {
            iconWidth = SPACE_FOR_ICON;
            if (height < iconWidth / 2) {
                iconHeight = iconWidth / 2;
            }
        }

        // @formatter:off
        Size size = Size.newSize()
                .width(width + iconWidth)
                .height(height + iconHeight)
                .build();

        // Sprotty needs the inverse of the x and y for the alignment, so it's "0 - x" and "0 - y" on purpose
        Position alignment = Position.newPosition()
                .x(0 - stringBounds.getX() + iconWidth)
                .y(0 - stringBounds.getY())
                .build();
        // @formatter:on

        return new TextBounds(size, alignment);
    }
}
