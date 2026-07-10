/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

package org.eclipse.sirius.web.application.messages;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Used to provide internationalized messages.
 *
 * @author mcharfadi
 */
@Service
public class SiriusWebApplicationMessageService implements ISiriusWebApplicationMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public SiriusWebApplicationMessageService(@Qualifier("siriusWebApplicationMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String treeToolExpandAll() {
        return this.messageSourceAccessor.getMessage("TREE_TOOL_EXPAND_ALL");
    }

    @Override
    public String treeToolDownload() {
        return this.messageSourceAccessor.getMessage("TREE_TOOL_DOWNLOAD");
    }

    @Override
    public String treeToolNewObject() {
        return this.messageSourceAccessor.getMessage("TREE_TOOL_NEW_OBJECT");
    }

    @Override
    public String treeToolNewRepresentation() {
        return this.messageSourceAccessor.getMessage("TREE_TOOL_NEW_REPRESENTATION");
    }

    @Override
    public String treeToolDuplicateObject() {
        return this.messageSourceAccessor.getMessage("TREE_TOOL_DUPLICATE_OBJECT");
    }

    @Override
    public String treeToolDuplicateRepresentation() {
        return this.messageSourceAccessor.getMessage("TREE_TOOL_DUPLICATE_REPRESENTATION");
    }

    @Override
    public String treeToolUpdateLibrary() {
        return this.messageSourceAccessor.getMessage("TREE_TOOL_UPDATE_LIBRARY");
    }

    @Override
    public String treeToolRemoveLibrary() {
        return this.messageSourceAccessor.getMessage("TREE_TOOL_REMOVE_LIBRARY");
    }

}
