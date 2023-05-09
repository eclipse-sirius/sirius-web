/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.web.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.sirius.components.core.api.FeedbackLevel;
import org.eclipse.sirius.components.core.api.FeedbackMessage;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Service used to stack the feedback messages during a tool operation.
 * The scope of this bean must be {@link RequestScope}, because it only concerns the current operation.
 *
 * @author frouene
 */
@Service
@RequestScope
public class FeedbackMessageService implements IFeedbackMessageService {


    private final List<FeedbackMessage> feedbackMessages = Collections.synchronizedList(new ArrayList<>());


    @Override
    public void addFeedbackMessage(String message, FeedbackLevel level) {
        this.feedbackMessages.add(new FeedbackMessage(message, level));
    }

    @Override
    public List<String> getFeedbackMessages() {
        return this.feedbackMessages
                .stream()
                .map(this::applyLevelEmoji)
                .toList();
    }

    private String applyLevelEmoji(FeedbackMessage feedback) {
        String prefix = switch (feedback.level()) {
            case DEBUG -> new String(Character.toChars(0x1F41B));
            case INFO -> new String(Character.toChars(0x2139));
            case WARNING -> new String(Character.toChars(0x26A0));
            case ERROR -> new String(Character.toChars(0x274C));
        };
        return String.format("%s %s", prefix, feedback.message());
    }

}
