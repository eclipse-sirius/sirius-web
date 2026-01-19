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

import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Style Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getLineStyle <em>Line Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getSourceArrowStyle <em>Source Arrow
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getTargetArrowStyle <em>Target Arrow
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getEdgeWidth <em>Edge Width</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#isShowIcon <em>Show Icon</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getLabelIcon <em>Label Icon</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getBackground <em>Background</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getMaxWidthExpression <em>Max Width
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getEdgeType <em>Edge Type</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription()
 * @model
 * @generated
 */
public interface EdgeStyleDescription extends Style, LabelStyle, BorderStyle {
    /**
     * Returns the value of the '<em><b>Line Style</b></em>' attribute. The default value is <code>"Solid"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.view.diagram.LineStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Line Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see #setLineStyle(LineStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_LineStyle()
     * @model default="Solid" required="true"
     * @generated
     */
    LineStyle getLineStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getLineStyle
     * <em>Line Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_SourceArrowStyle()
     * @model default="None" required="true"
     * @generated
     */
    ArrowStyle getSourceArrowStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getSourceArrowStyle
     * <em>Source Arrow Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_TargetArrowStyle()
     * @model default="InputArrow" required="true"
     * @generated
     */
    ArrowStyle getTargetArrowStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getTargetArrowStyle
     * <em>Target Arrow Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_EdgeWidth()
     * @model default="1" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     */
    int getEdgeWidth();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getEdgeWidth
     * <em>Edge Width</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_ShowIcon()
     * @model default="false"
     * @generated
     */
    boolean isShowIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#isShowIcon <em>Show
     * Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Show Icon</em>' attribute.
     * @see #isShowIcon()
     * @generated
     */
    void setShowIcon(boolean value);

    /**
     * Returns the value of the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Icon</em>' attribute.
     * @see #setLabelIcon(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_LabelIcon()
     * @model
     * @generated
     */
    String getLabelIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getLabelIcon
     * <em>Label Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Icon</em>' attribute.
     * @see #getLabelIcon()
     * @generated
     */
    void setLabelIcon(String value);

    /**
     * Returns the value of the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Background</em>' reference.
     * @see #setBackground(UserColor)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_Background()
     * @model
     * @generated
     */
    UserColor getBackground();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getBackground
     * <em>Background</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background</em>' reference.
     * @see #getBackground()
     * @generated
     */
    void setBackground(UserColor value);

    /**
     * Returns the value of the '<em><b>Max Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Max Width Expression</em>' attribute.
     * @see #setMaxWidthExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_MaxWidthExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getMaxWidthExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getMaxWidthExpression <em>Max Width
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Max Width Expression</em>' attribute.
     * @see #getMaxWidthExpression()
     * @generated
     */
    void setMaxWidthExpression(String value);

    /**
     * Returns the value of the '<em><b>Edge Type</b></em>' attribute. The default value is <code>"Manhattan"</code>.
     * The literals are from the enumeration {@link org.eclipse.sirius.components.view.diagram.EdgeType}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Edge Type</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.EdgeType
     * @see #setEdgeType(EdgeType)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_EdgeType()
     * @model default="Manhattan" required="true"
     * @generated
     */
    EdgeType getEdgeType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getEdgeType
     * <em>Edge Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Edge Type</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.EdgeType
     * @see #getEdgeType()
     * @generated
     */
    void setEdgeType(EdgeType value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeStyleDescription_Description()
     * @model
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeStyleDescription#getDescription
     * <em>Description</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

} // EdgeStyleDescription
