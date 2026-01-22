/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.view.table;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Cell Textfield Widget Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.table.TablePackage#getCellTextfieldWidgetDescription()
 * @model
 * @generated
 */
public interface CellTextfieldWidgetDescription extends CellWidgetDescription {

    /**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirius.components.view.Operation}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference list.
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getCellTextfieldWidgetDescription_Body()
	 * @model containment="true"
	 * @generated
	 */
    EList<Operation> getBody();

} // CellTextfieldWidgetDescription
