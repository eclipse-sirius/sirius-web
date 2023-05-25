/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.TextBoundsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Utility class used to compute the size of a piece of text.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class TextBoundsService {

    private final Logger logger = LoggerFactory.getLogger(TextBoundsService.class);

    private final TextBoundsProvider textBoundsProvider;

    private final ExecutorService executorService;

    public TextBoundsService() {
        this.textBoundsProvider = new TextBoundsProvider();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @PostConstruct
    public void initialize() {
        this.logger.debug("AWT initialization starting");

        Runnable computeBounds = () -> {

            // @formatter:off
            LabelStyle labelStyle = LabelStyle.newLabelStyle()
                    .fontSize(16)
                    .color("#000000")
                    .iconURL("")
                    .build();
            Label label = Label.newLabel(UUID.randomUUID().toString())
                    .type("labelType")
                    .position(Position.UNDEFINED)
                    .size(Size.UNDEFINED)
                    .alignment(Position.UNDEFINED)
                    .text("ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
                    .style(labelStyle)
                    .build();
            this.getBounds(label);
            //@formatter:on
        };

        this.executorService.execute(computeBounds);
        this.executorService.shutdown();

        this.logger.debug("AWT initialization done");
    }

    @PreDestroy
    public void beandestroy() {
        if (this.executorService != null) {
            this.executorService.shutdownNow();
        }
    }

    public TextBounds getBounds(Label label) {
        return this.textBoundsProvider.computeBounds(label.getStyle(), label.getText());
    }

    public TextBounds getBounds(InsideLabel insideLabel) {
        return this.textBoundsProvider.computeBounds(insideLabel.getStyle(), insideLabel.getText());
    }

    public TextBounds getAutoWrapBounds(Label label, double maxWidth) {
        return this.textBoundsProvider.computeAutoWrapBounds(label.getStyle(), label.getText(), maxWidth);
    }

    public TextBounds getAutoWrapBounds(InsideLabel insideLabel, double maxWidth) {
        return this.textBoundsProvider.computeAutoWrapBounds(insideLabel.getStyle(), insideLabel.getText(), maxWidth);
    }

}
