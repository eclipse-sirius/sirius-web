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
package org.eclipse.sirius.web.application.views.explorer.services.api;

import java.util.List;

import org.eclipse.sirius.web.application.views.explorer.dto.ExplorerDescriptionMetadata;

/**
 * Interface to sort tree descriptions that could be used inside the explorer of Sirius web.
 *
 * @author frouene
 */
public interface IExplorerTreeDescriptionOrderer {

    void order(List<ExplorerDescriptionMetadata> explorerDescriptionMetadataList);
}
