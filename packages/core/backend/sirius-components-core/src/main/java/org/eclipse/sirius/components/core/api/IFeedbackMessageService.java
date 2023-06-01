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
package org.eclipse.sirius.components.core.api;

import java.util.List;

import org.eclipse.sirius.components.representations.Message;

/**
 * Interface of the service interacting with the list of stacked feedback messages.
 *
 * @author frouene
 */
public interface IFeedbackMessageService {

    void addFeedbackMessage(Message message);

    List<Message> getFeedbackMessages();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements IFeedbackMessageService {

        @Override
        public void addFeedbackMessage(Message message) {

        }

        @Override
        public List<Message> getFeedbackMessages() {
            return List.of();
        }
    }
}
