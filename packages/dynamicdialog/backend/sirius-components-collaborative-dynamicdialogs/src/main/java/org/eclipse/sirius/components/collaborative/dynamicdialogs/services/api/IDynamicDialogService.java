/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.dynamicdialogs.services.api;

import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.ApplyDynamicDialogInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogQueryBasedObjectsInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.GetDynamicDialogValidationMessagesInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The service related to the dynamic dialog.
 *
 * @author Laurent Fasani
 */
public interface IDynamicDialogService {

    /**
     * Provide the dynamic dialog for this object.
     *
     * @param objectId
     *            the id of the semantic object associated to the wizard
     */
    IPayload renderDynamicDialog(IEditingContext editingContext, DynamicDialogInput dynamicDialogInput);

    IPayload getQueryBasedObjects(IEditingContext editingContext, DynamicDialogQueryBasedObjectsInput dynamicDialogQueryBasedObjectsInput);

    /**
     * Apply the changes described as interpreted expression on the dynamic dialog description.</br>
     */
    IPayload applyDialog(IEditingContext editingContext, ApplyDynamicDialogInput input);

    /**
     * Apply the changes described as interpreted expression on the dynamic dialog description.</br>
     */
    IPayload getValidationMessages(IEditingContext editingContext, GetDynamicDialogValidationMessagesInput input);
}
