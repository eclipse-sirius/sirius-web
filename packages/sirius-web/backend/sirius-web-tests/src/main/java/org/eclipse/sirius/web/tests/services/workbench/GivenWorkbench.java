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
package org.eclipse.sirius.web.tests.services.workbench;

import org.eclipse.sirius.components.graphql.tests.EditingContextEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.RelatedElementsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.RepresentationsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.ValidationEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Utility class used to configure the workbench for the tests.
 *
 * @author sbegaudeau
 */
@Service
public class GivenWorkbench {

    @Autowired
    private EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @Autowired
    private ValidationEventSubscriptionRunner validationEventSubscriptionRunner;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private RelatedElementsEventSubscriptionRunner relatedElementsEventSubscriptionRunner;

    @Autowired
    private RepresentationsEventSubscriptionRunner representationsEventSubscriptionRunner;

    public Workbench.Builder onEditingContext(String editingContextId) {
        return new Workbench.Builder(editingContextId)
                .editingContextEventSubscriptionRunner(editingContextEventSubscriptionRunner)
                .explorerEventSubscriptionRunner(explorerEventSubscriptionRunner)
                .validationEventSubscriptionRunner(validationEventSubscriptionRunner)
                .detailsEventSubscriptionRunner(detailsEventSubscriptionRunner)
                .relatedElementsEventSubscriptionRunner(relatedElementsEventSubscriptionRunner)
                .representationsEventSubscriptionRunner(representationsEventSubscriptionRunner);
    }
}
