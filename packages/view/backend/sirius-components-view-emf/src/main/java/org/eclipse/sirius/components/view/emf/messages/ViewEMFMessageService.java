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

    @Override
    public String invokeActionError(String actionName) {
        return this.messageSourceAccessor.getMessage("INVOKE_ACTION_ERROR", new Object[] { actionName });
    }

    @Override
    public String coreProperties() {
        return this.messageSourceAccessor.getMessage("CORE_PROPERTIES");
    }

    public String defaultQuickToolAdjustSize() {
        return this.messageSourceAccessor.getMessage("ADJUST_SIZE");
    }

    @Override
    public String defaultQuickToolResetOutsideLabelPosition() {
        return this.messageSourceAccessor.getMessage("RESET_OUTSIDE_LABEL_POSITION");
    }

    @Override
    public String defaultQuickToolResetLabelSize() {
        return this.messageSourceAccessor.getMessage("RESET_LABEL_SIZE");
    }

    @Override
    public String defaultQuickToolResetHandlesPosition() {
        return this.messageSourceAccessor.getMessage("RESET_HANDLES_POSITION");
    }

    @Override
    public String defaultQuickToolResetBendingPoints() {
        return this.messageSourceAccessor.getMessage("RESET_BENDING_POINTS");
    }

    @Override
    public String defaultQuickToolCollapse() {
        return this.messageSourceAccessor.getMessage("COLLAPSE");
    }

    @Override
    public String defaultQuickToolExpand() {
        return this.messageSourceAccessor.getMessage("EXPAND");
    }

    @Override
    public String defaultQuickToolDeleteFromModel() {
        return this.messageSourceAccessor.getMessage("DELETE_FROM_MODEl");
    }

    @Override
    public String defaultQuickToolFade() {
        return this.messageSourceAccessor.getMessage("FADE");
    }

    @Override
    public String defaultQuickToolUnFade() {
        return this.messageSourceAccessor.getMessage("UNFADE");
    }

    @Override
    public String defaultQuickToolHide() {
        return this.messageSourceAccessor.getMessage("HIDE");
    }

    @Override
    public String defaultQuickToolPin() {
        return this.messageSourceAccessor.getMessage("PIN");
    }

    @Override
    public String defaultQuickToolUnPin() {
        return this.messageSourceAccessor.getMessage("UNPIN");
    }

    @Override
    public String defaultQuickToolEdit() {
        return this.messageSourceAccessor.getMessage("EDIT");
    }

    @Override
    public String defaultSelectionDialogTitle() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_TITLE");
    }

    @Override
    public String defaultSelectionDialogWithOptionalSelectionDescription() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_WITH_OPTIONAL_SELECTION_DESCRIPTION");
    }

    @Override
    public String defaultSelectionDialogWithMandatorySelectionDescription() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_WITH_MANDATORY_SELECTION_DESCRIPTION");
    }

    @Override
    public String defaultSelectionDialogNoSelectionActionLabel() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_NO_SELECTION_ACTION_LABEL");
    }

    @Override
    public String defaultSelectionDialogNoSelectionActionDescription() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_NO_SELECTION_ACTION_DESCRIPTION");
    }

    @Override
    public String defaultSelectionDialogWithSelectionActionLabel() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_WITH_SELECTION_ACTION_LABEL");
    }

    @Override
    public String defaultSelectionDialogWithSelectionActionDescription() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_WITH_SELECTION_ACTION_DESCRIPTION");
    }

    @Override
    public String defaultSelectionDialogNoSelectionActionStatusMessage() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_NO_SELECTION_ACTION_STATUS_MESSAGE");
    }

    @Override
    public String defaultSelectionDialogSelectionRequiredWithoutSelectionStatusMessage() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE");
    }

    @Override
    public String defaultSelectionDialogSelectionRequiredWithOneSelectedElementStatusMessage(String element) {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_SELECTION_REQUIRED_WITH_ONE_SELECTED_ELEMENT_STATUS_MESSAGE", new Object[] { element });
    }

    @Override
    public String defaultSelectionDialogSelectionRequiredWithManySelectedElementsStatusMessage(long count) {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_SELECTION_REQUIRED_WITH_MANY_SELECTED_ELEMENTS_STATUS_MESSAGE", new Object[] { count });
    }

    @Override
    public String defaultSelectionDialogConfirmButtonLabel() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_CONFIRM_BUTTON_LABEL");
    }

    @Override
    public String defaultSelectionDialogSelectionRequiredWithoutSelectionConfirmButtonLabel() {
        return this.messageSourceAccessor.getMessage("DEFAULT_SELECTION_DIALOG_SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL");
    }
}
