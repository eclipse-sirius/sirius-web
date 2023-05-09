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

import java.util.Objects;

/**
 * Record used to represent a feedback message.
 *
 * @author frouene
 */
public record FeedbackMessage(String message, FeedbackLevel level) {

    public FeedbackMessage {
        Objects.requireNonNull(message);
        Objects.requireNonNull(level);
    }

}
