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
package org.eclipse.sirius.web.spring.collaborative.forms.handlers;

import java.util.Optional;

import org.eclipse.sirius.web.collaborative.forms.api.IFormService;
import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Form;

/**
 * Implementation of the form service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpFormService implements IFormService {

    @Override
    public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
        return Optional.empty();
    }

}
