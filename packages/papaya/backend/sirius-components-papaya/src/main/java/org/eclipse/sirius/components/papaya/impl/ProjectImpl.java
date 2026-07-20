/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.papaya.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Folder;
import org.eclipse.sirius.components.papaya.FolderElement;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Project;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Project</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getFolders <em>Folders</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getHomepage <em>Homepage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends NamedElementImpl implements Project {
    /**
	 * The cached value of the '{@link #getFolders() <em>Folders</em>}' containment reference list.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getFolders()
	 * @generated
	 * @ordered
	 */
    protected EList<Folder> folders;

    /**
     * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getElements()
     * @generated
     * @ordered
     */
    protected EList<FolderElement> elements;

    /**
	 * The default value of the '{@link #getHomepage() <em>Homepage</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getHomepage()
	 * @generated
	 * @ordered
	 */
    protected static final String HOMEPAGE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getHomepage() <em>Homepage</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getHomepage()
	 * @generated
	 * @ordered
	 */
    protected String homepage = HOMEPAGE_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ProjectImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PapayaPackage.Literals.PROJECT;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Folder> getFolders() {
		if (folders == null)
		{
			folders = new EObjectContainmentEList<Folder>(Folder.class, this, PapayaPackage.PROJECT__FOLDERS);
		}
		return folders;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<FolderElement> getElements() {
		if (elements == null)
		{
			elements = new EObjectContainmentEList<FolderElement>(FolderElement.class, this, PapayaPackage.PROJECT__ELEMENTS);
		}
		return elements;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getHomepage() {
		return homepage;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setHomepage(String newHomepage) {
		String oldHomepage = homepage;
		homepage = newHomepage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.PROJECT__HOMEPAGE, oldHomepage, homepage));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case PapayaPackage.PROJECT__FOLDERS:
				return ((InternalEList<?>)getFolders()).basicRemove(otherEnd, msgs);
			case PapayaPackage.PROJECT__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case PapayaPackage.PROJECT__FOLDERS:
				return getFolders();
			case PapayaPackage.PROJECT__ELEMENTS:
				return getElements();
			case PapayaPackage.PROJECT__HOMEPAGE:
				return getHomepage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case PapayaPackage.PROJECT__FOLDERS:
				getFolders().clear();
				getFolders().addAll((Collection<? extends Folder>)newValue);
				return;
			case PapayaPackage.PROJECT__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends FolderElement>)newValue);
				return;
			case PapayaPackage.PROJECT__HOMEPAGE:
				setHomepage((String)newValue);
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
			case PapayaPackage.PROJECT__FOLDERS:
				getFolders().clear();
				return;
			case PapayaPackage.PROJECT__ELEMENTS:
				getElements().clear();
				return;
			case PapayaPackage.PROJECT__HOMEPAGE:
				setHomepage(HOMEPAGE_EDEFAULT);
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
			case PapayaPackage.PROJECT__FOLDERS:
				return folders != null && !folders.isEmpty();
			case PapayaPackage.PROJECT__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case PapayaPackage.PROJECT__HOMEPAGE:
				return HOMEPAGE_EDEFAULT == null ? homepage != null : !HOMEPAGE_EDEFAULT.equals(homepage);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == org.eclipse.sirius.components.papaya.Container.class)
		{
			switch (derivedFeatureID)
			{
				case PapayaPackage.PROJECT__FOLDERS: return PapayaPackage.CONTAINER__FOLDERS;
				case PapayaPackage.PROJECT__ELEMENTS: return PapayaPackage.CONTAINER__ELEMENTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == org.eclipse.sirius.components.papaya.Container.class)
		{
			switch (baseFeatureID)
			{
				case PapayaPackage.CONTAINER__FOLDERS: return PapayaPackage.PROJECT__FOLDERS;
				case PapayaPackage.CONTAINER__ELEMENTS: return PapayaPackage.PROJECT__ELEMENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (homepage: ");
		result.append(homepage);
		result.append(')');
		return result.toString();
	}

} // ProjectImpl
