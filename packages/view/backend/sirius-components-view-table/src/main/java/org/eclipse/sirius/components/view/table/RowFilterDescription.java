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
package org.eclipse.sirius.components.view.table;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Row Filter Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.table.RowFilterDescription#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.RowFilterDescription#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.RowFilterDescription#getInitialStateExpression <em>Initial State Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.table.TablePackage#getRowFilterDescription()
 * @model
 * @generated
 */
public interface RowFilterDescription extends EObject {

    /**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getRowFilterDescription_Id()
	 * @model dataType="org.eclipse.sirius.components.view.Identifier" required="true"
	 * @generated
	 */
    String getId();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowFilterDescription#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowFilterDescriptin_LablExpression()
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RoFilterDescription#getLabelExpression
     * <em>Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Label Expression</em>' attribute.
     * @generted
     * @see #getLabelExpression()
     */
    void setLabelExpression(String value);

    /**
	 * Returns the value of the '<em><b>Initial State Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State Expression</em>' attribute.
	 * @see #setInitialStateExpression(String)
	 * @see org.eclipse.sirius.components.view.table.TablePackage#getRowFilterDescription_InitialStateExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getInitialStateExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowFilterDescription#getInitialStateExpression <em>Initial State Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State Expression</em>' attribute.
	 * @see #getInitialStateExpression()
	 * @generated
	 */
    void setInitialStateExpression(String value);

} // RowFilterDescription
