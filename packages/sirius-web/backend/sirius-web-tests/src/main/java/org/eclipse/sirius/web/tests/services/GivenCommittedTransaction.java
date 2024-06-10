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
package org.eclipse.sirius.web.tests.services;

import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to commit the state of the transaction after its initialization by the @Sql annotation
 * in order to make the state persisted in the database. Without this, the initialized state
 * will not be visible by the various repositories when the test will switch threads to use
 * the thread of the editing context.
 *
 * This should not be used every single time but only in the couple integrations tests that are
 * required to interact with repositories while inside an editing context event handler or a
 * representation event handler for example.
 *
 * @author sbegaudeau
 */
@Service
public class GivenCommittedTransaction implements IGivenCommittedTransaction {
    @Override
    public void commit() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }
}
