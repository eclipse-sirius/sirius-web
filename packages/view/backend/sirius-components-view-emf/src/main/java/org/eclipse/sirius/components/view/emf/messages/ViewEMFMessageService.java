/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

package org.eclipse.sirius.components.view.emf.messages;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Used to provide internationalized messages.
 *
 * @author Jerome Gout
 */
@Service
public class ViewEMFMessageService implements IViewEMFMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public ViewEMFMessageService(@Qualifier("viewEMFMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String tableCellEditError() {
        return this.messageSourceAccessor.getMessage("TABLE_CELL_EDIT_ERROR");
    }

    @Override
    public String unsupportedTableCellWidgetError() {
        return this.messageSourceAccessor.getMessage("UNSUPPORTED_TABLE_CELL_WIDGET_ERROR");
    }
}
