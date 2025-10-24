/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.services.editingcontext;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * Adapter used to store the logging information on the resource set.
 *
 * @author sbegaudeau
 */
public class LoggingAdapter extends AdapterImpl {

    private final List<String> messages;

    public LoggingAdapter(List<String> messages) {
        this.messages = Objects.requireNonNull(messages);
    }

    public List<String> getMessages() {
        return this.messages;
    }
}
