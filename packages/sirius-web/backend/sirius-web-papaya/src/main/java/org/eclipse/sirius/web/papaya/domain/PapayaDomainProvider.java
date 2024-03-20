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
package org.eclipse.sirius.web.papaya.domain;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.web.application.studio.services.api.IDomainProvider;
import org.springframework.stereotype.Service;

/**
 * Used to create the test domain.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaDomainProvider implements IDomainProvider {

    @Override
    public List<Domain> getDomains(IEditingContext editingContext) {
        return new PapayaDomainFactory().getDomains();
    }
}
