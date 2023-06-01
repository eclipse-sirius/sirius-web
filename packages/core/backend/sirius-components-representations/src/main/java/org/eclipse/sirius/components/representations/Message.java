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
package org.eclipse.sirius.components.representations;

import java.util.Objects;

/**
 * Record used to represent a return with a specific level.
 *
 * @author frouene
 */
public record Message(String body, MessageLevel level) {

    public Message {
        Objects.requireNonNull(body);
        Objects.requireNonNull(level);
    }
}
