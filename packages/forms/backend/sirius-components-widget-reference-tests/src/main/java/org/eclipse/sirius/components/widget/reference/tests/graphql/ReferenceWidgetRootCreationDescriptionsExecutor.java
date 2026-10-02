/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.widget.reference.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

/**
 * Used to retrieve root creation descriptions and perform assertions on the result.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceWidgetRootCreationDescriptionsExecutor {

    private final ReferenceWidgetRootCreationDescriptionsQueryRunner referenceWidgetRootCreationDescriptionsQueryRunner;

    public ReferenceWidgetRootCreationDescriptionsExecutor(ReferenceWidgetRootCreationDescriptionsQueryRunner referenceWidgetRootCreationDescriptionsQueryRunner) {
        this.referenceWidgetRootCreationDescriptionsQueryRunner = Objects.requireNonNull(referenceWidgetRootCreationDescriptionsQueryRunner);
    }

    public ReferenceWidgetRootCreationDescriptionsAssert execute(Map<String, Object> variables) {
        return new ReferenceWidgetRootCreationDescriptionsAssert(this.referenceWidgetRootCreationDescriptionsQueryRunner.run(variables));
    }
}
