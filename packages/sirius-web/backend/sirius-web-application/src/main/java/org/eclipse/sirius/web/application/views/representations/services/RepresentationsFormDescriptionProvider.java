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
package org.eclipse.sirius.web.application.views.representations.services;

import org.eclipse.sirius.components.collaborative.forms.api.IRepresentationsDescriptionProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.springframework.stereotype.Service;

/**
 * Provides the form description for the representations view.
 *
 * @author gcoutable
 */
@Service
public class RepresentationsFormDescriptionProvider implements IRepresentationsDescriptionProvider {
    @Override
    public FormDescription getRepresentationsDescription() {
        return null;
    }
}
