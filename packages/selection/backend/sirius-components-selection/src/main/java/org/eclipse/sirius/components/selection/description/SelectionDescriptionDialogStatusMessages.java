/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.selection.description;

import java.util.Objects;

/**
 * The different status messages for the selection dialog that could be used.
 *
 * <p>
 * There is a third status message that depend on elements selected in the selection dialog tree representation.
 * This third status message is handled by the SelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageDataFetcher.
 * </p>
 *
 * @author gcoutable
 */
public record SelectionDescriptionDialogStatusMessages(String noSelectionActionStatusMessage, String selectionRequiredWithoutSelectionStatusMessage) {
    public SelectionDescriptionDialogStatusMessages {
        Objects.requireNonNull(noSelectionActionStatusMessage);
        Objects.requireNonNull(selectionRequiredWithoutSelectionStatusMessage);
    }
}
