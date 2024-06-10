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
package org.eclipse.sirius.web.services.hierarchy;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.charts.HierarchyEventInput;
import org.eclipse.sirius.components.collaborative.charts.HierarchyRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.services.api.IGivenCreatedHierarchySubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

import reactor.core.publisher.Flux;

/**
 * Used to create a hierarchy and subscribe to it.
 *
 * @author pcdavid
 */
@Service
public class GivenCreatedHierarchySubscription implements IGivenCreatedHierarchySubscription {

    private final IGivenCommittedTransaction givenCommittedTransaction;

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final HierarchyEventSubscriptionRunner hierarchyEventSubscriptionRunner;

    public GivenCreatedHierarchySubscription(IGivenCommittedTransaction givenCommittedTransaction, IGivenCreatedRepresentation givenCreatedRepresentation, HierarchyEventSubscriptionRunner hierarchyEventSubscriptionRunner) {
        this.givenCommittedTransaction = Objects.requireNonNull(givenCommittedTransaction);
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.hierarchyEventSubscriptionRunner = Objects.requireNonNull(hierarchyEventSubscriptionRunner);
    }

    @Override
    public Flux<HierarchyRefreshedEventPayload> createAndSubscribe(CreateRepresentationInput input) {
        this.givenCommittedTransaction.commit();

        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var portalEventInput = new HierarchyEventInput(UUID.randomUUID(), input.editingContextId(), UUID.fromString(representationId));
        var flux = this.hierarchyEventSubscriptionRunner.run(portalEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return flux.filter(HierarchyRefreshedEventPayload.class::isInstance)
                .map(HierarchyRefreshedEventPayload.class::cast);
    }
}
