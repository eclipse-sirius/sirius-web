/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.services.portals;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRefreshedEventPayload;
import org.eclipse.sirius.components.portals.tests.graphql.PortalEventSubscriptionRunner;
import org.eclipse.sirius.web.services.api.IGivenCreatedPortalSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;

/**
 * Used to create a portal and subscribe to it.
 *
 * @author pcdavid
 */
@Service
public class GivenCreatedPortalSubscription implements IGivenCreatedPortalSubscription {

    private final IGivenCommittedTransaction givenCommittedTransaction;

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final PortalEventSubscriptionRunner portalEventSubscriptionRunner;

    public GivenCreatedPortalSubscription(IGivenCommittedTransaction givenCommittedTransaction, IGivenCreatedRepresentation givenCreatedRepresentation, PortalEventSubscriptionRunner portalEventSubscriptionRunner) {
        this.givenCommittedTransaction = Objects.requireNonNull(givenCommittedTransaction);
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.portalEventSubscriptionRunner = Objects.requireNonNull(portalEventSubscriptionRunner);
    }

    @Override
    public Flux<PortalRefreshedEventPayload> createAndSubscribe(CreateRepresentationInput input) {
        this.givenCommittedTransaction.commit();

        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var portalEventInput = new PortalEventInput(UUID.randomUUID(), input.editingContextId(), representationId);
        var flux = this.portalEventSubscriptionRunner.run(portalEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return flux.filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast);
    }
}
