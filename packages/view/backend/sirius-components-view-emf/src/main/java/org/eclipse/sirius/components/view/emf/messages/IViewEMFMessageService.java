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

    String defaultSelectionDialogTitle();

    String defaultSelectionDialogWithOptionalSelectionDescription();

    String defaultSelectionDialogWithMandatorySelectionDescription();

    String defaultSelectionDialogNoSelectionActionLabel();

    String defaultSelectionDialogNoSelectionActionDescription();

    String defaultSelectionDialogWithSelectionActionLabel();

    String defaultSelectionDialogWithSelectionActionDescription();

    String defaultSelectionDialogNoSelectionActionStatusMessage();

    String defaultSelectionDialogSelectionRequiredWithoutSelectionStatusMessage();

    String defaultSelectionDialogSelectionRequiredWithOneSelectedElementStatusMessage(String element);

    String defaultSelectionDialogSelectionRequiredWithManySelectedElementsStatusMessage(long count);

    String defaultSelectionDialogConfirmButtonLabel();

    String defaultSelectionDialogSelectionRequiredWithoutSelectionConfirmButtonLabel();

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

        @Override
        public String defaultSelectionDialogTitle() {
            return "";
        }

        @Override
        public String defaultSelectionDialogWithOptionalSelectionDescription() {
            return "";
        }

        @Override
        public String defaultSelectionDialogWithMandatorySelectionDescription() {
            return "";
        }

        @Override
        public String defaultSelectionDialogNoSelectionActionLabel() {
            return "";
        }

        @Override
        public String defaultSelectionDialogNoSelectionActionDescription() {
            return "";
        }

        @Override
        public String defaultSelectionDialogWithSelectionActionLabel() {
            return "";
        }

        @Override
        public String defaultSelectionDialogWithSelectionActionDescription() {
            return "";
        }

        @Override
        public String defaultSelectionDialogNoSelectionActionStatusMessage() {
            return "";
        }

        @Override
        public String defaultSelectionDialogSelectionRequiredWithoutSelectionStatusMessage() {
            return "";
        }

        @Override
        public String defaultSelectionDialogSelectionRequiredWithOneSelectedElementStatusMessage(String element) {
            return "";
        }

        @Override
        public String defaultSelectionDialogSelectionRequiredWithManySelectedElementsStatusMessage(long count) {
            return "";
        }

        @Override
        public String defaultSelectionDialogConfirmButtonLabel() {
            return "";
        }

        @Override
        public String defaultSelectionDialogSelectionRequiredWithoutSelectionConfirmButtonLabel() {
            return "";
        }
    }
}
