/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.api;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedBooleanInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedObjectInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedObjectsInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedStringInput;

/**
 * Common interface for services capable of executing a Query.
 *
 * @author fbarbin
 */
public interface IQueryService {
    IPayload execute(IEditingContext editingContext, QueryBasedStringInput input);

    IPayload execute(IEditingContext editingContext, QueryBasedIntInput input);

    IPayload execute(IEditingContext editingContext, QueryBasedBooleanInput input);

    IPayload execute(IEditingContext editingContext, QueryBasedObjectInput input);

    IPayload execute(IEditingContext editingContext, QueryBasedObjectsInput input);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author fbarbin
     */
    class NoOp implements IQueryService {

        @Override
        public IPayload execute(IEditingContext editingContext, QueryBasedStringInput input) {
            return null;
        }

        @Override
        public IPayload execute(IEditingContext editingContext, QueryBasedIntInput input) {
            return null;
        }

        @Override
        public IPayload execute(IEditingContext editingContext, QueryBasedBooleanInput input) {
            return null;
        }

        @Override
        public IPayload execute(IEditingContext editingContext, QueryBasedObjectInput input) {
            return null;
        }

        @Override
        public IPayload execute(IEditingContext editingContext, QueryBasedObjectsInput input) {
            return null;
        }

    }
}
