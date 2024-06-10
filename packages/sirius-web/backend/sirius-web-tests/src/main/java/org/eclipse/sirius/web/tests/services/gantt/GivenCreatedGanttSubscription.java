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
package org.eclipse.sirius.web.tests.services.gantt;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.GanttRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.GanttEventInput;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedGanttSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;

/**
 * Used to create a gantt and subscribe to it.
 *
 * @author sbegaudeau
 */
@Service
public class GivenCreatedGanttSubscription implements IGivenCreatedGanttSubscription {

    private final IGivenCommittedTransaction givenCommittedTransaction;

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final GanttEventSubscriptionRunner ganttEventSubscriptionRunner;

    public GivenCreatedGanttSubscription(IGivenCommittedTransaction givenCommittedTransaction, IGivenCreatedRepresentation givenCreatedRepresentation, GanttEventSubscriptionRunner ganttEventSubscriptionRunner) {
        this.givenCommittedTransaction = Objects.requireNonNull(givenCommittedTransaction);
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.ganttEventSubscriptionRunner = Objects.requireNonNull(ganttEventSubscriptionRunner);
    }

    @Override
    public Flux<GanttRefreshedEventPayload> createAndSubscribe(CreateRepresentationInput input) {
        this.givenCommittedTransaction.commit();

        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var ganttEventInput = new GanttEventInput(UUID.randomUUID(), input.editingContextId(), UUID.fromString(representationId));
        var flux = this.ganttEventSubscriptionRunner.run(ganttEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return flux.filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(GanttRefreshedEventPayload.class::isInstance)
                .map(GanttRefreshedEventPayload.class::cast);
    }
}
