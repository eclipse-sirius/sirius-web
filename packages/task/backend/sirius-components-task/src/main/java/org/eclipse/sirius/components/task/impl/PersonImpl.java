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
package org.eclipse.sirius.components.task.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.TaskPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Person</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.task.impl.PersonImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.PersonImpl#getBiography <em>Biography</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.task.impl.PersonImpl#getImageUrl <em>Image Url</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PersonImpl extends ResourceImpl implements Person {
    /**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
    protected static final String ALIAS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getAlias()
     * @generated
     * @ordered
     */
    protected String alias = ALIAS_EDEFAULT;

    /**
	 * The default value of the '{@link #getBiography() <em>Biography</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBiography()
	 * @generated
	 * @ordered
	 */
    protected static final String BIOGRAPHY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getBiography() <em>Biography</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBiography()
	 * @generated
	 * @ordered
	 */
    protected String biography = BIOGRAPHY_EDEFAULT;

    /**
	 * The default value of the '{@link #getImageUrl() <em>Image Url</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getImageUrl()
	 * @generated
	 * @ordered
	 */
    protected static final String IMAGE_URL_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getImageUrl() <em>Image Url</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getImageUrl()
	 * @generated
	 * @ordered
	 */
    protected String imageUrl = IMAGE_URL_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected PersonImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TaskPackage.Literals.PERSON;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getAlias() {
		return alias;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.PERSON__ALIAS, oldAlias, alias));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getBiography() {
		return biography;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBiography(String newBiography) {
		String oldBiography = biography;
		biography = newBiography;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.PERSON__BIOGRAPHY, oldBiography, biography));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getImageUrl() {
		return imageUrl;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setImageUrl(String newImageUrl) {
		String oldImageUrl = imageUrl;
		imageUrl = newImageUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskPackage.PERSON__IMAGE_URL, oldImageUrl, imageUrl));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case TaskPackage.PERSON__ALIAS:
				return getAlias();
			case TaskPackage.PERSON__BIOGRAPHY:
				return getBiography();
			case TaskPackage.PERSON__IMAGE_URL:
				return getImageUrl();
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
			case TaskPackage.PERSON__ALIAS:
				setAlias((String)newValue);
				return;
			case TaskPackage.PERSON__BIOGRAPHY:
				setBiography((String)newValue);
				return;
			case TaskPackage.PERSON__IMAGE_URL:
				setImageUrl((String)newValue);
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
			case TaskPackage.PERSON__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case TaskPackage.PERSON__BIOGRAPHY:
				setBiography(BIOGRAPHY_EDEFAULT);
				return;
			case TaskPackage.PERSON__IMAGE_URL:
				setImageUrl(IMAGE_URL_EDEFAULT);
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
			case TaskPackage.PERSON__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case TaskPackage.PERSON__BIOGRAPHY:
				return BIOGRAPHY_EDEFAULT == null ? biography != null : !BIOGRAPHY_EDEFAULT.equals(biography);
			case TaskPackage.PERSON__IMAGE_URL:
				return IMAGE_URL_EDEFAULT == null ? imageUrl != null : !IMAGE_URL_EDEFAULT.equals(imageUrl);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (alias: ");
		result.append(alias);
		result.append(", biography: ");
		result.append(biography);
		result.append(", imageUrl: ");
		result.append(imageUrl);
		result.append(')');
		return result.toString();
	}

} // PersonImpl
