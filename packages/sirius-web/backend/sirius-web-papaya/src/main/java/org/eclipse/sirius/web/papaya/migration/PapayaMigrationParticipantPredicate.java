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
package org.eclipse.sirius.web.papaya.migration;

import java.util.Objects;

import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.papaya.services.api.IPapayaCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to indicate that the Papaya editing context will need migration participants.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaMigrationParticipantPredicate implements IEditingContextMigrationParticipantPredicate {

    private final IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate;

    public PapayaMigrationParticipantPredicate(IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate) {
        this.papayaCapableEditingContextPredicate = Objects.requireNonNull(papayaCapableEditingContextPredicate);
    }

    @Override
    public boolean test(String editingContextId) {
        return this.papayaCapableEditingContextPredicate.test(editingContextId);
    }
}
