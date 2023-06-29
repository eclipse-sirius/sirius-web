/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Label Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.LabelStyle#getFontSize <em>Font Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.LabelStyle#isItalic <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.LabelStyle#isBold <em>Bold</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.LabelStyle#isUnderline <em>Underline</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.LabelStyle#isStrikeThrough <em>Strike Through</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getLabelStyle()
 * @model abstract="true"
 * @generated
 */
public interface LabelStyle extends EObject {
    /**
     * Returns the value of the '<em><b>Font Size</b></em>' attribute. The default value is <code>"14"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Font Size</em>' attribute.
     * @see #setFontSize(int)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLabelStyle_FontSize()
     * @model default="14" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     */
    int getFontSize();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.LabelStyle#getFontSize <em>Font Size</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Font Size</em>' attribute.
     * @see #getFontSize()
     * @generated
     */
    void setFontSize(int value);

    /**
     * Returns the value of the '<em><b>Italic</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Italic</em>' attribute.
     * @see #setItalic(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLabelStyle_Italic()
     * @model default="false" required="true"
     * @generated
     */
    boolean isItalic();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.LabelStyle#isItalic <em>Italic</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Italic</em>' attribute.
     * @see #isItalic()
     * @generated
     */
    void setItalic(boolean value);

    /**
     * Returns the value of the '<em><b>Bold</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Bold</em>' attribute.
     * @see #setBold(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLabelStyle_Bold()
     * @model default="false" required="true"
     * @generated
     */
    boolean isBold();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.LabelStyle#isBold <em>Bold</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Bold</em>' attribute.
     * @see #isBold()
     * @generated
     */
    void setBold(boolean value);

    /**
     * Returns the value of the '<em><b>Underline</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Underline</em>' attribute.
     * @see #setUnderline(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLabelStyle_Underline()
     * @model default="false" required="true"
     * @generated
     */
    boolean isUnderline();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.LabelStyle#isUnderline <em>Underline</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Underline</em>' attribute.
     * @see #isUnderline()
     * @generated
     */
    void setUnderline(boolean value);

    /**
     * Returns the value of the '<em><b>Strike Through</b></em>' attribute. The default value is <code>"false"</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Strike Through</em>' attribute.
     * @see #setStrikeThrough(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLabelStyle_StrikeThrough()
     * @model default="false" required="true"
     * @generated
     */
    boolean isStrikeThrough();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.LabelStyle#isStrikeThrough <em>Strike
     * Through</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Strike Through</em>' attribute.
     * @see #isStrikeThrough()
     * @generated
     */
    void setStrikeThrough(boolean value);

} // LabelStyle
