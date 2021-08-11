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
package org.eclipse.sirius.web.spring.collaborative.forms.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.spring.collaborative.dto.Subscriber;

/**
 * Used to obtain the subscribers of a specific widget.
 *
 * @author sbegaudeau
 */
@GraphQLObjectType
public class WidgetSubscription {
    private final String widgetId;

    private final List<Subscriber> subscribers;

    public WidgetSubscription(String widgetId, List<Subscriber> subscribers) {
        this.widgetId = Objects.requireNonNull(widgetId);
        this.subscribers = Objects.requireNonNull(subscribers);
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getWidgetId() {
        return this.widgetId;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<Subscriber> getSubscribers() {
        return this.subscribers;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'widgetId: {1}, subscribers: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.widgetId, this.subscribers);
    }
}
