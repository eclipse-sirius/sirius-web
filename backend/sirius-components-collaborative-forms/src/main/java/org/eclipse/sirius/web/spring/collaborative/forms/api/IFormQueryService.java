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
package org.eclipse.sirius.web.spring.collaborative.forms.api;

import java.util.Optional;

import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Form;

/**
 * Used to find information in a form.
 *
 * @author sbegaudeau
 */
public interface IFormQueryService {
    Optional<AbstractWidget> findWidget(Form form, String widgetId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IFormQueryService {

        @Override
        public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
            return Optional.empty();
        }

    }
}
