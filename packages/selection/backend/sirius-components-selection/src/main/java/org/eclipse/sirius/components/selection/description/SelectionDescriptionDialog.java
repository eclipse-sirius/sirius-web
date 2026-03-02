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
 * The different values for the different parts of the selection dialog.
 *
 * <p>
 *     It contains only customizable elements in the selection dialog that can be computed at the dialog opening.
 *     For instance, the status message that depend on the selection in the tree representation is user dependent, thus, it requires its own data fetcher.
 * </p>
 * @author gcoutable
 */
public record SelectionDescriptionDialog(SelectionDescriptionDialogTitles titles, String description, SelectionDescriptionDialogAction noSelectionAction, SelectionDescriptionDialogAction withSelectionAction, SelectionDescriptionDialogStatusMessages statusMessages, SelectionDescriptionDialogConfirmButtonLabels confirmButtonLabels) {
    public SelectionDescriptionDialog {
        Objects.requireNonNull(titles);
        Objects.requireNonNull(description);
        Objects.requireNonNull(noSelectionAction);
        Objects.requireNonNull(withSelectionAction);
        Objects.requireNonNull(statusMessages);
        Objects.requireNonNull(confirmButtonLabels);
    }
}
