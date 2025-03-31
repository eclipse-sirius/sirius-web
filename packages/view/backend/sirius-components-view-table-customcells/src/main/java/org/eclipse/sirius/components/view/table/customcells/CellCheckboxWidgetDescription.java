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
package org.eclipse.sirius.components.view.table.customcells;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Cell Checkbox Widget Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription#getBody
 * <em>Body</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.table.customcells.CustomcellsPackage#getCellCheckboxWidgetDescription()
 */
public interface CellCheckboxWidgetDescription extends CellWidgetDescription {

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.table.customcells.CustomcellsPackage#getCellCheckboxWidgetDescription_Body()
     */
    EList<Operation> getBody();

} // CellCheckboxWidgetDescription
