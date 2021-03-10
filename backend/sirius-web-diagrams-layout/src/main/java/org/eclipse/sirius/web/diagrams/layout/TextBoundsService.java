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

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.TextBounds;
import org.eclipse.sirius.web.diagrams.TextBoundsProvider;
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

    private final Logger logger = LoggerFactory.getLogger(TextBoundsService.class);

    private final TextBoundsProvider textBoundsProvider;

    private final ExecutorService executorService;

    public TextBoundsService() {
        this.textBoundsProvider = new TextBoundsProvider();
        this.executorService = Executors.newSingleThreadExecutor();
    }

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
            Label label = Label.newLabel(UUID.randomUUID())
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
        return this.textBoundsProvider.computeBounds(label.getStyle(), label.getText());
    }

}
