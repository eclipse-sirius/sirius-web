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
package org.eclipse.sirius.components.view.diagram;

import org.eclipse.sirius.components.view.LabelStyle;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLineStyle <em>Line Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getSourceArrowStyle <em>Source Arrow Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getTargetArrowStyle <em>Target Arrow Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getEdgeWidth <em>Edge Width</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#isShowIcon <em>Show Icon</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle()
 * @model
 * @generated
 */
public interface EdgeStyle extends Style, LabelStyle {
    /**
     * Returns the value of the '<em><b>Line Style</b></em>' attribute. The default value is <code>"Solid"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.view.diagram.LineStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Line Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see #setLineStyle(LineStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_LineStyle()
     * @model default="Solid" required="true"
     * @generated
     */
    LineStyle getLineStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLineStyle <em>Line
     * Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Line Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see #getLineStyle()
     * @generated
     */
    void setLineStyle(LineStyle value);

    /**
     * Returns the value of the '<em><b>Source Arrow Style</b></em>' attribute. The default value is
     * <code>"None"</code>. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.ArrowStyle}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Arrow Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see #setSourceArrowStyle(ArrowStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_SourceArrowStyle()
     * @model default="None" required="true"
     * @generated
     */
    ArrowStyle getSourceArrowStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getSourceArrowStyle <em>Source
     * Arrow Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Source Arrow Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see #getSourceArrowStyle()
     * @generated
     */
    void setSourceArrowStyle(ArrowStyle value);

    /**
     * Returns the value of the '<em><b>Target Arrow Style</b></em>' attribute. The default value is
     * <code>"InputArrow"</code>. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.ArrowStyle}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Arrow Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see #setTargetArrowStyle(ArrowStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_TargetArrowStyle()
     * @model default="InputArrow" required="true"
     * @generated
     */
    ArrowStyle getTargetArrowStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getTargetArrowStyle <em>Target
     * Arrow Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Target Arrow Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see #getTargetArrowStyle()
     * @generated
     */
    void setTargetArrowStyle(ArrowStyle value);

    /**
     * Returns the value of the '<em><b>Edge Width</b></em>' attribute. The default value is <code>"1"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Edge Width</em>' attribute.
     * @see #setEdgeWidth(int)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_EdgeWidth()
     * @model default="1" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     */
    int getEdgeWidth();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getEdgeWidth <em>Edge
     * Width</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Edge Width</em>' attribute.
     * @see #getEdgeWidth()
     * @generated
     */
    void setEdgeWidth(int value);

    /**
     * Returns the value of the '<em><b>Show Icon</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Show Icon</em>' attribute.
     * @see #setShowIcon(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_ShowIcon()
     * @model default="false"
     * @generated
     */
    boolean isShowIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#isShowIcon <em>Show
     * Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Show Icon</em>' attribute.
     * @see #isShowIcon()
     * @generated
     */
    void setShowIcon(boolean value);

} // EdgeStyle
