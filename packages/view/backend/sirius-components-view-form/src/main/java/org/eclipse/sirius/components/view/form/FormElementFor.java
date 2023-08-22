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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Element For</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.FormElementFor#getIterator <em>Iterator</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FormElementFor#getIterableExpression <em>Iterable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FormElementFor#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getFormElementFor()
 * @model
 * @generated
 */
public interface FormElementFor extends FormElementDescription {
    /**
     * Returns the value of the '<em><b>Iterator</b></em>' attribute. The default value is <code>"it"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Iterator</em>' attribute.
     * @see #setIterator(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFormElementFor_Iterator()
     * @model default="it" required="true"
     * @generated
     */
    String getIterator();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.FormElementFor#getIterator
     * <em>Iterator</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Iterator</em>' attribute.
     * @see #getIterator()
     * @generated
     */
    void setIterator(String value);

    /**
     * Returns the value of the '<em><b>Iterable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Iterable Expression</em>' attribute.
     * @see #setIterableExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFormElementFor_IterableExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getIterableExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.FormElementFor#getIterableExpression
     * <em>Iterable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Iterable Expression</em>' attribute.
     * @see #getIterableExpression()
     * @generated
     */
    void setIterableExpression(String value);

    /**
     * Returns the value of the '<em><b>Children</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.form.FormElementDescription}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Children</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFormElementFor_Children()
     * @model containment="true"
     * @generated
     */
    EList<FormElementDescription> getChildren();

} // FormElementFor
