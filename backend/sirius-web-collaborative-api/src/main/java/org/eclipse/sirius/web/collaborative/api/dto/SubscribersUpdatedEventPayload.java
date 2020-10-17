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
package org.eclipse.sirius.web.collaborative.api.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.services.api.dto.IPayload;

/**
 * Payload used to indicate that list of subscribers of the representation has been updated.
 *
 * @author hmarchadour
 */
@GraphQLObjectType
public final class SubscribersUpdatedEventPayload implements IPayload {

    private final List<Subscriber> subscribers;

    public SubscribersUpdatedEventPayload(List<Subscriber> subscribers) {
        this.subscribers = Objects.requireNonNull(subscribers);
    }

    @GraphQLField
    @GraphQLNonNull
    public List<org.eclipse.sirius.web.collaborative.api.dto.Subscriber> getSubscribers() {
        return this.subscribers;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName());
    }
}
