/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.deck.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.impl.LabelStyleImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Element Description Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.deck.impl.DeckElementDescriptionStyleImpl#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.deck.impl.DeckElementDescriptionStyleImpl#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeckElementDescriptionStyleImpl extends LabelStyleImpl implements DeckElementDescriptionStyle {
    /**
	 * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' reference.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getBackgroundColor()
	 * @generated
	 * @ordered
	 */
    protected UserColor backgroundColor;

    /**
     * The cached value of the '{@link #getColor() <em>Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getColor()
     * @generated
     * @ordered
     */
    protected UserColor color;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected DeckElementDescriptionStyleImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DeckPackage.Literals.DECK_ELEMENT_DESCRIPTION_STYLE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getBackgroundColor() {
		if (backgroundColor != null && backgroundColor.eIsProxy())
		{
			InternalEObject oldBackgroundColor = (InternalEObject)backgroundColor;
			backgroundColor = (UserColor)eResolveProxy(oldBackgroundColor);
			if (backgroundColor != oldBackgroundColor)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, backgroundColor));
			}
		}
		return backgroundColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetBackgroundColor() {
		return backgroundColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBackgroundColor(UserColor newBackgroundColor) {
		UserColor oldBackgroundColor = backgroundColor;
		backgroundColor = newBackgroundColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, backgroundColor));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getColor() {
		if (color != null && color.eIsProxy())
		{
			InternalEObject oldColor = (InternalEObject)color;
			color = (UserColor)eResolveProxy(oldColor);
			if (color != oldColor)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__COLOR, oldColor, color));
			}
		}
		return color;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetColor() {
		return color;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setColor(UserColor newColor) {
		UserColor oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__COLOR, oldColor, color));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
				if (resolve) return getBackgroundColor();
				return basicGetBackgroundColor();
			case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
				if (resolve) return getColor();
				return basicGetColor();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
				setBackgroundColor((UserColor)newValue);
				return;
			case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
				setColor((UserColor)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
				setBackgroundColor((UserColor)null);
				return;
			case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
				setColor((UserColor)null);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
				return backgroundColor != null;
			case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
				return color != null;
		}
		return super.eIsSet(featureID);
	}

} // DeckElementDescriptionStyleImpl
