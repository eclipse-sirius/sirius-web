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
package org.eclipse.sirius.components.papaya.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.papaya.DependencyLink;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.StartOrEnd;
import org.eclipse.sirius.components.papaya.Task;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Dependency Link</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.DependencyLinkImpl#getTargetKind <em>Target Kind</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.DependencyLinkImpl#getSourceKind <em>Source Kind</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.DependencyLinkImpl#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.DependencyLinkImpl#getDuration <em>Duration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DependencyLinkImpl extends MinimalEObjectImpl.Container implements DependencyLink {
    /**
	 * The default value of the '{@link #getTargetKind() <em>Target Kind</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getTargetKind()
	 * @generated
	 * @ordered
	 */
    protected static final StartOrEnd TARGET_KIND_EDEFAULT = StartOrEnd.START;

    /**
	 * The cached value of the '{@link #getTargetKind() <em>Target Kind</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getTargetKind()
	 * @generated
	 * @ordered
	 */
    protected StartOrEnd targetKind = TARGET_KIND_EDEFAULT;

    /**
	 * The default value of the '{@link #getSourceKind() <em>Source Kind</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getSourceKind()
	 * @generated
	 * @ordered
	 */
    protected static final StartOrEnd SOURCE_KIND_EDEFAULT = StartOrEnd.START;

    /**
	 * The cached value of the '{@link #getSourceKind() <em>Source Kind</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getSourceKind()
	 * @generated
	 * @ordered
	 */
    protected StartOrEnd sourceKind = SOURCE_KIND_EDEFAULT;

    /**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
    protected Task source;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected DependencyLinkImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PapayaPackage.Literals.DEPENDENCY_LINK;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public StartOrEnd getTargetKind() {
		return targetKind;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setTargetKind(StartOrEnd newTargetKind) {
		StartOrEnd oldTargetKind = targetKind;
		targetKind = newTargetKind == null ? TARGET_KIND_EDEFAULT : newTargetKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.DEPENDENCY_LINK__TARGET_KIND, oldTargetKind, targetKind));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public StartOrEnd getSourceKind() {
		return sourceKind;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSourceKind(StartOrEnd newSourceKind) {
		StartOrEnd oldSourceKind = sourceKind;
		sourceKind = newSourceKind == null ? SOURCE_KIND_EDEFAULT : newSourceKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.DEPENDENCY_LINK__SOURCE_KIND, oldSourceKind, sourceKind));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Task getSource() {
		if (source != null && source.eIsProxy())
		{
			InternalEObject oldSource = (InternalEObject)source;
			source = (Task)eResolveProxy(oldSource);
			if (source != oldSource)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PapayaPackage.DEPENDENCY_LINK__SOURCE, oldSource, source));
			}
		}
		return source;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public Task basicGetSource() {
		return source;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSource(Task newSource) {
		Task oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.DEPENDENCY_LINK__SOURCE, oldSource, source));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case PapayaPackage.DEPENDENCY_LINK__TARGET_KIND:
				return getTargetKind();
			case PapayaPackage.DEPENDENCY_LINK__SOURCE_KIND:
				return getSourceKind();
			case PapayaPackage.DEPENDENCY_LINK__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
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
			case PapayaPackage.DEPENDENCY_LINK__TARGET_KIND:
				setTargetKind((StartOrEnd)newValue);
				return;
			case PapayaPackage.DEPENDENCY_LINK__SOURCE_KIND:
				setSourceKind((StartOrEnd)newValue);
				return;
			case PapayaPackage.DEPENDENCY_LINK__SOURCE:
				setSource((Task)newValue);
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
			case PapayaPackage.DEPENDENCY_LINK__TARGET_KIND:
				setTargetKind(TARGET_KIND_EDEFAULT);
				return;
			case PapayaPackage.DEPENDENCY_LINK__SOURCE_KIND:
				setSourceKind(SOURCE_KIND_EDEFAULT);
				return;
			case PapayaPackage.DEPENDENCY_LINK__SOURCE:
				setSource((Task)null);
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
			case PapayaPackage.DEPENDENCY_LINK__TARGET_KIND:
				return targetKind != TARGET_KIND_EDEFAULT;
			case PapayaPackage.DEPENDENCY_LINK__SOURCE_KIND:
				return sourceKind != SOURCE_KIND_EDEFAULT;
			case PapayaPackage.DEPENDENCY_LINK__SOURCE:
				return source != null;
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
		result.append(" (targetKind: ");
		result.append(targetKind);
		result.append(", sourceKind: ");
		result.append(sourceKind);
		result.append(')');
		return result.toString();
	}

} // DependencyLinkImpl
