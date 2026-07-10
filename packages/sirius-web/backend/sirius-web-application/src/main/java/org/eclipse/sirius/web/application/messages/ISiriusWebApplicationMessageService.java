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

/**
 * Used to compute internationalized messages.
 *
 * @author Jerome Gout
 */
public interface ISiriusWebApplicationMessageService {

    String treeToolExpandAll();

    String treeToolDownload();

    String treeToolNewObject();

    String treeToolNewRepresentation();

    String treeToolDuplicateObject();

    String treeToolDuplicateRepresentation();

    String treeToolUpdateLibrary();

    String treeToolRemoveLibrary();
}
