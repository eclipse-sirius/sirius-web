/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getFolders <em>Folders</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getElements <em>Elements</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl#getHomepage <em>Homepage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends NamedElementImpl implements Project {
    /**
     * The cached value of the '{@link #getFolders() <em>Folders</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
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
     * The default value of the '{@link #getHomepage() <em>Homepage</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getHomepage()
     * @generated
     * @ordered
     */
    protected static final String HOMEPAGE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHomepage() <em>Homepage</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getHomepage()
     * @generated
     * @ordered
     */
    protected String homepage = HOMEPAGE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ProjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.PROJECT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Folder> getFolders() {
        if (this.folders == null) {
            this.folders = new EObjectContainmentEList<>(Folder.class, this, PapayaPackage.PROJECT__FOLDERS);
        }
        return this.folders;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<FolderElement> getElements() {
        if (this.elements == null) {
            this.elements = new EObjectContainmentEList<>(FolderElement.class, this, PapayaPackage.PROJECT__ELEMENTS);
        }
        return this.elements;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getHomepage() {
        return this.homepage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHomepage(String newHomepage) {
        String oldHomepage = this.homepage;
        this.homepage = newHomepage;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.PROJECT__HOMEPAGE, oldHomepage, this.homepage));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.PROJECT__FOLDERS:
                return ((InternalEList<?>) this.getFolders()).basicRemove(otherEnd, msgs);
            case PapayaPackage.PROJECT__ELEMENTS:
                return ((InternalEList<?>) this.getElements()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.PROJECT__FOLDERS:
                return this.getFolders();
            case PapayaPackage.PROJECT__ELEMENTS:
                return this.getElements();
            case PapayaPackage.PROJECT__HOMEPAGE:
                return this.getHomepage();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PapayaPackage.PROJECT__FOLDERS:
                this.getFolders().clear();
                this.getFolders().addAll((Collection<? extends Folder>) newValue);
                return;
            case PapayaPackage.PROJECT__ELEMENTS:
                this.getElements().clear();
                this.getElements().addAll((Collection<? extends FolderElement>) newValue);
                return;
            case PapayaPackage.PROJECT__HOMEPAGE:
                this.setHomepage((String) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case PapayaPackage.PROJECT__FOLDERS:
                this.getFolders().clear();
                return;
            case PapayaPackage.PROJECT__ELEMENTS:
                this.getElements().clear();
                return;
            case PapayaPackage.PROJECT__HOMEPAGE:
                this.setHomepage(HOMEPAGE_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case PapayaPackage.PROJECT__FOLDERS:
                return this.folders != null && !this.folders.isEmpty();
            case PapayaPackage.PROJECT__ELEMENTS:
                return this.elements != null && !this.elements.isEmpty();
            case PapayaPackage.PROJECT__HOMEPAGE:
                return HOMEPAGE_EDEFAULT == null ? this.homepage != null : !HOMEPAGE_EDEFAULT.equals(this.homepage);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == org.eclipse.sirius.components.papaya.Container.class) {
            switch (derivedFeatureID) {
                case PapayaPackage.PROJECT__FOLDERS:
                    return PapayaPackage.CONTAINER__FOLDERS;
                case PapayaPackage.PROJECT__ELEMENTS:
                    return PapayaPackage.CONTAINER__ELEMENTS;
                default:
                    return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == org.eclipse.sirius.components.papaya.Container.class) {
            switch (baseFeatureID) {
                case PapayaPackage.CONTAINER__FOLDERS:
                    return PapayaPackage.PROJECT__FOLDERS;
                case PapayaPackage.CONTAINER__ELEMENTS:
                    return PapayaPackage.PROJECT__ELEMENTS;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (homepage: ");
        result.append(this.homepage);
        result.append(')');
        return result.toString();
    }

} // ProjectImpl
