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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSnapshot;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import jakarta.validation.constraints.NotNull;

/**
 * A snapshot of an editing context.
 *
 * @author gdaniel
 */
public record EditingContextSnapshot(@NotNull List<DocumentData> documents,
        @NotNull Map<URI, Adapter> libraryAdapterResourcesMap,
        @NotNull List<AggregateReference<SemanticData, UUID>> dependencies) implements IEditingContextSnapshot {

}
