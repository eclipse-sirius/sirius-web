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
package org.eclipse.sirius.web.application.views.tree;


import java.util.Collections;
import java.util.List;

import org.eclipse.sirius.web.application.studio.services.representations.DomainViewTreeDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.dto.ExplorerDescriptionMetadata;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeDescriptionOrderer;
import org.springframework.stereotype.Service;

/**
 * Implementation used to sort tree descriptions that can be used as explorer for Domain, by placing the default explorer first.
 *
 * @author frouene
 */
@Service
public class DomainExplorerTreeDescriptionOrderer implements IExplorerTreeDescriptionOrderer {

    @Override
    public void order(List<ExplorerDescriptionMetadata> explorerDescriptionMetadataList) {
        if (explorerDescriptionMetadataList.stream().map(ExplorerDescriptionMetadata::label).anyMatch(label -> label.equals(DomainViewTreeDescriptionProvider.DOMAIN_EXPLORER_DESCRIPTION_NAME))) {
            Collections.reverse(explorerDescriptionMetadataList);
        }
    }
}
