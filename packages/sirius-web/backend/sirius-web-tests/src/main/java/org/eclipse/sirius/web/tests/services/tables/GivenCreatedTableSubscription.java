/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.web.tests.services.tables;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableEventInput;
import org.eclipse.sirius.components.tables.tests.graphql.TableEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedTableSubscription;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;
import reactor.core.publisher.Flux;

/**
 * Used to create a table and subscribe to it.
 *
 * @author frouene
 */
@Service
public class GivenCreatedTableSubscription implements IGivenCreatedTableSubscription {

    private final IGivenCommittedTransaction givenCommittedTransaction;

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final TableEventSubscriptionRunner tableEventSubscriptionRunner;

    public GivenCreatedTableSubscription(IGivenCommittedTransaction givenCommittedTransaction, IGivenCreatedRepresentation givenCreatedRepresentation,
            TableEventSubscriptionRunner tableEventSubscriptionRunner) {
        this.givenCommittedTransaction = Objects.requireNonNull(givenCommittedTransaction);
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.tableEventSubscriptionRunner = Objects.requireNonNull(tableEventSubscriptionRunner);
    }

    @Override
    public Flux<Object> createAndSubscribe(CreateRepresentationInput input) {
        this.givenCommittedTransaction.commit();

        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var tableEventInput = new TableEventInput(UUID.randomUUID(), input.editingContextId(), representationId);
        var flux = this.tableEventSubscriptionRunner.run(tableEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return flux;
    }
}
