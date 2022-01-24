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
package org.eclipse.sirius.web.compat.services.representations;

import org.eclipse.sirius.business.api.query.IdentifiedElementQuery;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;
import org.springframework.stereotype.Service;

/**
 * Provides labels for VSM elements in a way that is consistent with Sirius RCP.
 *
 * @author pcdavid
 */
@Service
public class IdentifiedElementLabelProvider {
    public String getLabel(IdentifiedElement elt) {
        return new IdentifiedElementQuery(elt).getLabel();
    }
}
