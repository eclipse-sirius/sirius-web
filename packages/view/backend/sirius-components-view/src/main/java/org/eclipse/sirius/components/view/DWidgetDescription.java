/**
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>DWidget Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.DWidgetDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DWidgetDescription#getLabelExpression <em>Label Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDWidgetDescription()
 * @model abstract="true"
 * @generated
 */
public interface DWidgetDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDWidgetDescription_Id()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DWidgetDescription#getId <em>Id</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDWidgetDescription_LabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DWidgetDescription#getLabelExpression <em>Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Initial Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Initial Value Expression</em>' attribute.
     * @see #setInitialValueExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDWidgetDescription_InitialValueExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getInitialValueExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DWidgetDescription#getInitialValueExpression
     * <em>Initial Value Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Initial Value Expression</em>' attribute.
     * @see #getInitialValueExpression()
     * @generated
     */
    void setInitialValueExpression(String value);

    /**
     * Returns the value of the '<em><b>Output</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Output</em>' containment reference.
     * @see #setOutput(DWidgetOutputDescription)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDWidgetDescription_Output()
     * @model containment="true"
     * @generated
     */
    DWidgetOutputDescription getOutput();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DWidgetDescription#getOutput <em>Output</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Output</em>' containment reference.
     * @see #getOutput()
     * @generated
     */
    void setOutput(DWidgetOutputDescription value);

    /**
     * Returns the value of the '<em><b>Inputs</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.DWidgetOutputDescription}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Inputs</em>' reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDWidgetDescription_Inputs()
     * @model
     * @generated
     */
    EList<DWidgetOutputDescription> getInputs();

} // DWidgetDescription
