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
package org.eclipse.sirius.components.view.diagram;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Decorator Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getPreconditionExpression <em>Precondition
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getIconURLExpression <em>Icon URL
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getPosition <em>Position</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDecoratorDescription()
 * @model abstract="true"
 * @generated
 */
public interface DecoratorDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDecoratorDescription_LabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getLabelExpression
     * <em>Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDecoratorDescription_PreconditionExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Precondition Expression</em>' attribute.
     * @see #getPreconditionExpression()
     * @generated
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Icon URL Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Icon URL Expression</em>' attribute.
     * @see #setIconURLExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDecoratorDescription_IconURLExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIconURLExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getIconURLExpression <em>Icon URL
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Icon URL Expression</em>' attribute.
     * @see #getIconURLExpression()
     * @generated
     */
    void setIconURLExpression(String value);

    /**
     * Returns the value of the '<em><b>Position</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.DecoratorPosition}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Position</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.DecoratorPosition
     * @see #setPosition(DecoratorPosition)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDecoratorDescription_Position()
     * @model
     * @generated
     */
    DecoratorPosition getPosition();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getPosition
     * <em>Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Position</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.DecoratorPosition
     * @see #getPosition()
     * @generated
     */
    void setPosition(DecoratorPosition value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDecoratorDescription_Name()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DecoratorDescription#getName
     * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // DecoratorDescription
