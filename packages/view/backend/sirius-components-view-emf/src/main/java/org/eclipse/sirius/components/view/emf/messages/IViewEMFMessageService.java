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

    String invokeActionError(String actionName);

    String coreProperties();

    String defaultQuickToolAdjustSize();

    String defaultQuickToolResetOutsideLabelPosition();

    String defaultQuickToolResetLabelSize();

    String defaultQuickToolResetHandlesPosition();

    String defaultQuickToolResetBendingPoints();

    String defaultQuickToolCollapse();

    String defaultQuickToolExpand();

    String defaultQuickToolDeleteFromModel();

    String defaultQuickToolFade();

    String defaultQuickToolUnFade();

    String defaultQuickToolHide();

    String defaultQuickToolPin();

    String defaultQuickToolUnPin();

    String defaultQuickToolEdit();

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

        @Override
        public String invokeActionError(String actionName) {
            return "";
        }

        @Override
        public String coreProperties() {
            return "";
        }

        public String defaultQuickToolAdjustSize() {
            return "";
        }

        @Override
        public String defaultQuickToolResetOutsideLabelPosition() {
            return "";
        }

        @Override
        public String defaultQuickToolResetLabelSize() {
            return "";
        }

        @Override
        public String defaultQuickToolResetHandlesPosition() {
            return "";
        }

        @Override
        public String defaultQuickToolResetBendingPoints() {
            return "";
        }

        @Override
        public String defaultQuickToolCollapse() {
            return "";
        }

        @Override
        public String defaultQuickToolExpand() {
            return "";
        }

        @Override
        public String defaultQuickToolDeleteFromModel() {
            return "";
        }

        @Override
        public String defaultQuickToolFade() {
            return "";
        }

        @Override
        public String defaultQuickToolUnFade() {
            return "";
        }

        @Override
        public String defaultQuickToolHide() {
            return "";
        }

        @Override
        public String defaultQuickToolPin() {
            return "";
        }

        @Override
        public String defaultQuickToolUnPin() {
            return "";
        }

        @Override
        public String defaultQuickToolEdit() {
            return "";
        }
    }
}
