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

/**
 * Used to compute internationalized messages.
 *
 * @author Jerome Gout
 */
public interface IViewEMFMessageService {

    String tableCellEditError();

    String unsupportedTableCellWidgetError();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author Jerome Gout
     */
    class NoOp implements IViewEMFMessageService {

        @Override
        public String tableCellEditError() {
            return "";
        }

        @Override
        public String unsupportedTableCellWidgetError() {
            return "";
        }
    }
}
