/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Attribute;
import org.eclipse.sirius.components.papaya.Constructor;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.InterfaceImplementation;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Class</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#getImplements <em>Implements</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#isAbstract <em>Abstract</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#isFinal <em>Final</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#isStatic <em>Static</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#getExtendedBy <em>Extended By</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#getConstructors <em>Constructors</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#getAttributes <em>Attributes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ClassImpl#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassImpl extends ClassifierImpl implements org.eclipse.sirius.components.papaya.Class {
    /**
     * The cached value of the '{@link #getImplements() <em>Implements</em>}' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getImplements()
     * @generated
     * @ordered
     */
    protected EList<Interface> implements_;

    /**
     * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isAbstract()
     * @generated
     * @ordered
     */
    protected static final boolean ABSTRACT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isAbstract()
     * @generated
     * @ordered
     */
    protected boolean abstract_ = ABSTRACT_EDEFAULT;

    /**
     * The default value of the '{@link #isFinal() <em>Final</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isFinal()
     * @generated
     * @ordered
     */
    protected static final boolean FINAL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isFinal() <em>Final</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isFinal()
     * @generated
     * @ordered
     */
    protected boolean final_ = FINAL_EDEFAULT;

    /**
     * The default value of the '{@link #isStatic() <em>Static</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isStatic()
     * @generated
     * @ordered
     */
    protected static final boolean STATIC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isStatic() <em>Static</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isStatic()
     * @generated
     * @ordered
     */
    protected boolean static_ = STATIC_EDEFAULT;

    /**
     * The cached value of the '{@link #getExtends() <em>Extends</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getExtends()
     * @generated
     * @ordered
     */
    protected org.eclipse.sirius.components.papaya.Class extends_;

    /**
     * The cached value of the '{@link #getExtendedBy() <em>Extended By</em>}' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getExtendedBy()
     * @generated
     * @ordered
     */
    protected EList<org.eclipse.sirius.components.papaya.Class> extendedBy;

    /**
     * The cached value of the '{@link #getConstructors() <em>Constructors</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConstructors()
     * @generated
     * @ordered
     */
    protected EList<Constructor> constructors;

    /**
     * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAttributes()
     * @generated
     * @ordered
     */
    protected EList<Attribute> attributes;

    /**
     * The cached value of the '{@link #getOperations() <em>Operations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOperations()
     * @generated
     * @ordered
     */
    protected EList<Operation> operations;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ClassImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.CLASS;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isAbstract() {
        return this.abstract_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAbstract(boolean newAbstract) {
        boolean oldAbstract = this.abstract_;
        this.abstract_ = newAbstract;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.CLASS__ABSTRACT, oldAbstract, this.abstract_));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isFinal() {
        return this.final_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFinal(boolean newFinal) {
        boolean oldFinal = this.final_;
        this.final_ = newFinal;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.CLASS__FINAL, oldFinal, this.final_));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isStatic() {
        return this.static_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStatic(boolean newStatic) {
        boolean oldStatic = this.static_;
        this.static_ = newStatic;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.CLASS__STATIC, oldStatic, this.static_));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Interface> getImplements() {
        if (this.implements_ == null) {
            this.implements_ = new EObjectWithInverseResolvingEList.ManyInverse<>(Interface.class, this, PapayaPackage.CLASS__IMPLEMENTS, PapayaPackage.INTERFACE__IMPLEMENTED_BY);
        }
        return this.implements_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public org.eclipse.sirius.components.papaya.Class getExtends() {
        if (this.extends_ != null && this.extends_.eIsProxy()) {
            InternalEObject oldExtends = (InternalEObject) this.extends_;
            this.extends_ = (org.eclipse.sirius.components.papaya.Class) this.eResolveProxy(oldExtends);
            if (this.extends_ != oldExtends) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, PapayaPackage.CLASS__EXTENDS, oldExtends, this.extends_));
            }
        }
        return this.extends_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public org.eclipse.sirius.components.papaya.Class basicGetExtends() {
        return this.extends_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetExtends(org.eclipse.sirius.components.papaya.Class newExtends, NotificationChain msgs) {
        org.eclipse.sirius.components.papaya.Class oldExtends = this.extends_;
        this.extends_ = newExtends;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapayaPackage.CLASS__EXTENDS, oldExtends, newExtends);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setExtends(org.eclipse.sirius.components.papaya.Class newExtends) {
        if (newExtends != this.extends_) {
            NotificationChain msgs = null;
            if (this.extends_ != null)
                msgs = ((InternalEObject) this.extends_).eInverseRemove(this, PapayaPackage.CLASS__EXTENDED_BY, org.eclipse.sirius.components.papaya.Class.class, msgs);
            if (newExtends != null)
                msgs = ((InternalEObject) newExtends).eInverseAdd(this, PapayaPackage.CLASS__EXTENDED_BY, org.eclipse.sirius.components.papaya.Class.class, msgs);
            msgs = this.basicSetExtends(newExtends, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.CLASS__EXTENDS, newExtends, newExtends));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<org.eclipse.sirius.components.papaya.Class> getExtendedBy() {
        if (this.extendedBy == null) {
            this.extendedBy = new EObjectWithInverseResolvingEList<>(org.eclipse.sirius.components.papaya.Class.class, this, PapayaPackage.CLASS__EXTENDED_BY, PapayaPackage.CLASS__EXTENDS);
        }
        return this.extendedBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Constructor> getConstructors() {
        if (this.constructors == null) {
            this.constructors = new EObjectContainmentEList<>(Constructor.class, this, PapayaPackage.CLASS__CONSTRUCTORS);
        }
        return this.constructors;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Attribute> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new EObjectContainmentEList<>(Attribute.class, this, PapayaPackage.CLASS__ATTRIBUTES);
        }
        return this.attributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getOperations() {
        if (this.operations == null) {
            this.operations = new EObjectContainmentEList<>(Operation.class, this, PapayaPackage.CLASS__OPERATIONS);
        }
        return this.operations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.CLASS__IMPLEMENTS:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getImplements()).basicAdd(otherEnd, msgs);
            case PapayaPackage.CLASS__EXTENDS:
                if (this.extends_ != null)
                    msgs = ((InternalEObject) this.extends_).eInverseRemove(this, PapayaPackage.CLASS__EXTENDED_BY, org.eclipse.sirius.components.papaya.Class.class, msgs);
                return this.basicSetExtends((org.eclipse.sirius.components.papaya.Class) otherEnd, msgs);
            case PapayaPackage.CLASS__EXTENDED_BY:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getExtendedBy()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.CLASS__IMPLEMENTS:
                return ((InternalEList<?>) this.getImplements()).basicRemove(otherEnd, msgs);
            case PapayaPackage.CLASS__EXTENDS:
                return this.basicSetExtends(null, msgs);
            case PapayaPackage.CLASS__EXTENDED_BY:
                return ((InternalEList<?>) this.getExtendedBy()).basicRemove(otherEnd, msgs);
            case PapayaPackage.CLASS__CONSTRUCTORS:
                return ((InternalEList<?>) this.getConstructors()).basicRemove(otherEnd, msgs);
            case PapayaPackage.CLASS__ATTRIBUTES:
                return ((InternalEList<?>) this.getAttributes()).basicRemove(otherEnd, msgs);
            case PapayaPackage.CLASS__OPERATIONS:
                return ((InternalEList<?>) this.getOperations()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.CLASS__IMPLEMENTS:
                return this.getImplements();
            case PapayaPackage.CLASS__ABSTRACT:
                return this.isAbstract();
            case PapayaPackage.CLASS__FINAL:
                return this.isFinal();
            case PapayaPackage.CLASS__STATIC:
                return this.isStatic();
            case PapayaPackage.CLASS__EXTENDS:
                if (resolve)
                    return this.getExtends();
                return this.basicGetExtends();
            case PapayaPackage.CLASS__EXTENDED_BY:
                return this.getExtendedBy();
            case PapayaPackage.CLASS__CONSTRUCTORS:
                return this.getConstructors();
            case PapayaPackage.CLASS__ATTRIBUTES:
                return this.getAttributes();
            case PapayaPackage.CLASS__OPERATIONS:
                return this.getOperations();
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
            case PapayaPackage.CLASS__IMPLEMENTS:
                this.getImplements().clear();
                this.getImplements().addAll((Collection<? extends Interface>) newValue);
                return;
            case PapayaPackage.CLASS__ABSTRACT:
                this.setAbstract((Boolean) newValue);
                return;
            case PapayaPackage.CLASS__FINAL:
                this.setFinal((Boolean) newValue);
                return;
            case PapayaPackage.CLASS__STATIC:
                this.setStatic((Boolean) newValue);
                return;
            case PapayaPackage.CLASS__EXTENDS:
                this.setExtends((org.eclipse.sirius.components.papaya.Class) newValue);
                return;
            case PapayaPackage.CLASS__EXTENDED_BY:
                this.getExtendedBy().clear();
                this.getExtendedBy().addAll((Collection<? extends org.eclipse.sirius.components.papaya.Class>) newValue);
                return;
            case PapayaPackage.CLASS__CONSTRUCTORS:
                this.getConstructors().clear();
                this.getConstructors().addAll((Collection<? extends Constructor>) newValue);
                return;
            case PapayaPackage.CLASS__ATTRIBUTES:
                this.getAttributes().clear();
                this.getAttributes().addAll((Collection<? extends Attribute>) newValue);
                return;
            case PapayaPackage.CLASS__OPERATIONS:
                this.getOperations().clear();
                this.getOperations().addAll((Collection<? extends Operation>) newValue);
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
            case PapayaPackage.CLASS__IMPLEMENTS:
                this.getImplements().clear();
                return;
            case PapayaPackage.CLASS__ABSTRACT:
                this.setAbstract(ABSTRACT_EDEFAULT);
                return;
            case PapayaPackage.CLASS__FINAL:
                this.setFinal(FINAL_EDEFAULT);
                return;
            case PapayaPackage.CLASS__STATIC:
                this.setStatic(STATIC_EDEFAULT);
                return;
            case PapayaPackage.CLASS__EXTENDS:
                this.setExtends((org.eclipse.sirius.components.papaya.Class) null);
                return;
            case PapayaPackage.CLASS__EXTENDED_BY:
                this.getExtendedBy().clear();
                return;
            case PapayaPackage.CLASS__CONSTRUCTORS:
                this.getConstructors().clear();
                return;
            case PapayaPackage.CLASS__ATTRIBUTES:
                this.getAttributes().clear();
                return;
            case PapayaPackage.CLASS__OPERATIONS:
                this.getOperations().clear();
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
            case PapayaPackage.CLASS__IMPLEMENTS:
                return this.implements_ != null && !this.implements_.isEmpty();
            case PapayaPackage.CLASS__ABSTRACT:
                return this.abstract_ != ABSTRACT_EDEFAULT;
            case PapayaPackage.CLASS__FINAL:
                return this.final_ != FINAL_EDEFAULT;
            case PapayaPackage.CLASS__STATIC:
                return this.static_ != STATIC_EDEFAULT;
            case PapayaPackage.CLASS__EXTENDS:
                return this.extends_ != null;
            case PapayaPackage.CLASS__EXTENDED_BY:
                return this.extendedBy != null && !this.extendedBy.isEmpty();
            case PapayaPackage.CLASS__CONSTRUCTORS:
                return this.constructors != null && !this.constructors.isEmpty();
            case PapayaPackage.CLASS__ATTRIBUTES:
                return this.attributes != null && !this.attributes.isEmpty();
            case PapayaPackage.CLASS__OPERATIONS:
                return this.operations != null && !this.operations.isEmpty();
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
        if (baseClass == InterfaceImplementation.class) {
            switch (derivedFeatureID) {
                case PapayaPackage.CLASS__IMPLEMENTS:
                    return PapayaPackage.INTERFACE_IMPLEMENTATION__IMPLEMENTS;
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
        if (baseClass == InterfaceImplementation.class) {
            switch (baseFeatureID) {
                case PapayaPackage.INTERFACE_IMPLEMENTATION__IMPLEMENTS:
                    return PapayaPackage.CLASS__IMPLEMENTS;
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
        result.append(" (abstract: ");
        result.append(this.abstract_);
        result.append(", final: ");
        result.append(this.final_);
        result.append(", static: ");
        result.append(this.static_);
        result.append(')');
        return result.toString();
    }

} // ClassImpl
