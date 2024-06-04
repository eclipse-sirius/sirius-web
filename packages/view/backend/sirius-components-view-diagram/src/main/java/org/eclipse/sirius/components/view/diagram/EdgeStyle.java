/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import org.eclipse.sirius.components.view.UserColor;

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
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLabelIcon <em>Label Icon</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getBackground <em>Background</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle()
 */
public interface EdgeStyle extends Style, LabelStyle, BorderStyle {

    /**
     * Returns the value of the '<em><b>Line Style</b></em>' attribute. The default value is <code>"Solid"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.view.diagram.LineStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Line Style</em>' attribute.
     * @model default="Solid" required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see #setLineStyle(LineStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_LineStyle()
     */
    LineStyle getLineStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLineStyle <em>Line
     * Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Line Style</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see #getLineStyle()
     */
    void setLineStyle(LineStyle value);

    /**
     * Returns the value of the '<em><b>Source Arrow Style</b></em>' attribute. The default value is
     * <code>"None"</code>. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.ArrowStyle}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Arrow Style</em>' attribute.
     * @model default="None" required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see #setSourceArrowStyle(ArrowStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_SourceArrowStyle()
     */
    ArrowStyle getSourceArrowStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getSourceArrowStyle <em>Source
     * Arrow Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Source Arrow Style</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see #getSourceArrowStyle()
     */
    void setSourceArrowStyle(ArrowStyle value);

    /**
     * Returns the value of the '<em><b>Target Arrow Style</b></em>' attribute. The default value is
     * <code>"InputArrow"</code>. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.ArrowStyle}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Arrow Style</em>' attribute.
     * @model default="InputArrow" required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see #setTargetArrowStyle(ArrowStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_TargetArrowStyle()
     */
    ArrowStyle getTargetArrowStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getTargetArrowStyle <em>Target
     * Arrow Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Target Arrow Style</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see #getTargetArrowStyle()
     */
    void setTargetArrowStyle(ArrowStyle value);

    /**
     * Returns the value of the '<em><b>Edge Width</b></em>' attribute. The default value is <code>"1"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Edge Width</em>' attribute.
     * @model default="1" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     * @see #setEdgeWidth(int)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_EdgeWidth()
     */
    int getEdgeWidth();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getEdgeWidth <em>Edge
     * Width</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Edge Width</em>' attribute.
     * @generated
     * @see #getEdgeWidth()
     */
    void setEdgeWidth(int value);

    /**
     * Returns the value of the '<em><b>Show Icon</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Show Icon</em>' attribute.
     * @model default="false"
     * @generated
     * @see #setShowIcon(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_ShowIcon()
     */
    boolean isShowIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#isShowIcon <em>Show
     * Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Show Icon</em>' attribute.
     * @generated
     * @see #isShowIcon()
     */
    void setShowIcon(boolean value);

    /**
     * Returns the value of the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Icon</em>' attribute.
     * @model
     * @generated
     * @see #setLabelIcon(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_LabelIcon()
     */
    String getLabelIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLabelIcon <em>Label
     * Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Label Icon</em>' attribute.
     * @generated
     * @see #getLabelIcon()
     */
    void setLabelIcon(String value);

    /**
     * Returns the value of the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Background</em>' reference.
     * @model
     * @generated
     * @see #setBackground(UserColor)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyle_Background()
     */
    UserColor getBackground();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getBackground
     * <em>Background</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Background</em>' reference.
     * @generated
     * @see #getBackground()
     */
    void setBackground(UserColor value);

} // EdgeStyle
