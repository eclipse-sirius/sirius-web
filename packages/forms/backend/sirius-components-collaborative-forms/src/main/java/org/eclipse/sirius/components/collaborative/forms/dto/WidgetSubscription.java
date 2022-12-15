/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.dto.Subscriber;

/**
 * Used to obtain the subscribers of a specific widget.
 *
 * @author sbegaudeau
 */
public class WidgetSubscription {
    private final String widgetId;

    private final List<Subscriber> subscribers;

    public WidgetSubscription(String widgetId, List<Subscriber> subscribers) {
        this.widgetId = Objects.requireNonNull(widgetId);
        this.subscribers = Objects.requireNonNull(subscribers);
    }

    public String getWidgetId() {
        return this.widgetId;
    }

    public List<Subscriber> getSubscribers() {
        return this.subscribers;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'widgetId: {1}, subscribers: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.widgetId, this.subscribers);
    }
}
