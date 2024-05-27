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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.ComponentPort;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.ProvidedService;
import org.eclipse.sirius.components.papaya.RequiredService;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Component</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getDependencies <em>Dependencies</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getAllDependencies <em>All Dependencies</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getUsedAsDependencyBy <em>Used As Dependency
 * By</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getComponents <em>Components</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getAllComponents <em>All Components</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getPackages <em>Packages</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getPorts <em>Ports</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getProvidedServices <em>Provided
 * Services</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl#getRequiredServices <em>Required
 * Services</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentImpl extends NamedElementImpl implements Component {
    /**
     * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getDependencies()
     * @generated
     * @ordered
     */
    protected EList<Component> dependencies;

    /**
     * The cached value of the '{@link #getUsedAsDependencyBy() <em>Used As Dependency By</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getUsedAsDependencyBy()
     * @generated
     * @ordered
     */
    protected EList<Component> usedAsDependencyBy;

    /**
     * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getComponents()
     * @generated
     * @ordered
     */
    protected EList<Component> components;

    /**
     * The cached value of the '{@link #getPackages() <em>Packages</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPackages()
     * @generated
     * @ordered
     */
    protected EList<org.eclipse.sirius.components.papaya.Package> packages;

    /**
     * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPorts()
     * @generated
     * @ordered
     */
    protected EList<ComponentPort> ports;

    /**
     * The cached value of the '{@link #getProvidedServices() <em>Provided Services</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getProvidedServices()
     * @generated
     * @ordered
     */
    protected EList<ProvidedService> providedServices;

    /**
     * The cached value of the '{@link #getRequiredServices() <em>Required Services</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getRequiredServices()
     * @generated
     * @ordered
     */
    protected EList<RequiredService> requiredServices;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ComponentImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.COMPONENT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Component> getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new EObjectWithInverseResolvingEList.ManyInverse<>(Component.class, this, PapayaPackage.COMPONENT__DEPENDENCIES, PapayaPackage.COMPONENT__USED_AS_DEPENDENCY_BY);
        }
        return this.dependencies;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Component> getAllDependencies() {
        // TODO: implement this method to return the 'All Dependencies' reference list
        // Ensure that you remove @generated or mark it @generated NOT
        // The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and
        // org.eclipse.emf.ecore.EStructuralFeature.Setting
        // so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Component> getUsedAsDependencyBy() {
        if (this.usedAsDependencyBy == null) {
            this.usedAsDependencyBy = new EObjectWithInverseResolvingEList.ManyInverse<>(Component.class, this, PapayaPackage.COMPONENT__USED_AS_DEPENDENCY_BY, PapayaPackage.COMPONENT__DEPENDENCIES);
        }
        return this.usedAsDependencyBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Component> getComponents() {
        if (this.components == null) {
            this.components = new EObjectContainmentEList<>(Component.class, this, PapayaPackage.COMPONENT__COMPONENTS);
        }
        return this.components;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Component> getAllComponents() {
        // TODO: implement this method to return the 'All Components' reference list
        // Ensure that you remove @generated or mark it @generated NOT
        // The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and
        // org.eclipse.emf.ecore.EStructuralFeature.Setting
        // so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<org.eclipse.sirius.components.papaya.Package> getPackages() {
        if (this.packages == null) {
            this.packages = new EObjectContainmentEList<>(org.eclipse.sirius.components.papaya.Package.class, this, PapayaPackage.COMPONENT__PACKAGES);
        }
        return this.packages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ComponentPort> getPorts() {
        if (this.ports == null) {
            this.ports = new EObjectContainmentEList<>(ComponentPort.class, this, PapayaPackage.COMPONENT__PORTS);
        }
        return this.ports;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ProvidedService> getProvidedServices() {
        if (this.providedServices == null) {
            this.providedServices = new EObjectContainmentEList<>(ProvidedService.class, this, PapayaPackage.COMPONENT__PROVIDED_SERVICES);
        }
        return this.providedServices;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<RequiredService> getRequiredServices() {
        if (this.requiredServices == null) {
            this.requiredServices = new EObjectContainmentEList<>(RequiredService.class, this, PapayaPackage.COMPONENT__REQUIRED_SERVICES);
        }
        return this.requiredServices;
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
            case PapayaPackage.COMPONENT__DEPENDENCIES:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getDependencies()).basicAdd(otherEnd, msgs);
            case PapayaPackage.COMPONENT__USED_AS_DEPENDENCY_BY:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getUsedAsDependencyBy()).basicAdd(otherEnd, msgs);
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
            case PapayaPackage.COMPONENT__DEPENDENCIES:
                return ((InternalEList<?>) this.getDependencies()).basicRemove(otherEnd, msgs);
            case PapayaPackage.COMPONENT__USED_AS_DEPENDENCY_BY:
                return ((InternalEList<?>) this.getUsedAsDependencyBy()).basicRemove(otherEnd, msgs);
            case PapayaPackage.COMPONENT__COMPONENTS:
                return ((InternalEList<?>) this.getComponents()).basicRemove(otherEnd, msgs);
            case PapayaPackage.COMPONENT__PACKAGES:
                return ((InternalEList<?>) this.getPackages()).basicRemove(otherEnd, msgs);
            case PapayaPackage.COMPONENT__PORTS:
                return ((InternalEList<?>) this.getPorts()).basicRemove(otherEnd, msgs);
            case PapayaPackage.COMPONENT__PROVIDED_SERVICES:
                return ((InternalEList<?>) this.getProvidedServices()).basicRemove(otherEnd, msgs);
            case PapayaPackage.COMPONENT__REQUIRED_SERVICES:
                return ((InternalEList<?>) this.getRequiredServices()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.COMPONENT__DEPENDENCIES:
                return this.getDependencies();
            case PapayaPackage.COMPONENT__ALL_DEPENDENCIES:
                return this.getAllDependencies();
            case PapayaPackage.COMPONENT__USED_AS_DEPENDENCY_BY:
                return this.getUsedAsDependencyBy();
            case PapayaPackage.COMPONENT__COMPONENTS:
                return this.getComponents();
            case PapayaPackage.COMPONENT__ALL_COMPONENTS:
                return this.getAllComponents();
            case PapayaPackage.COMPONENT__PACKAGES:
                return this.getPackages();
            case PapayaPackage.COMPONENT__PORTS:
                return this.getPorts();
            case PapayaPackage.COMPONENT__PROVIDED_SERVICES:
                return this.getProvidedServices();
            case PapayaPackage.COMPONENT__REQUIRED_SERVICES:
                return this.getRequiredServices();
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
            case PapayaPackage.COMPONENT__DEPENDENCIES:
                this.getDependencies().clear();
                this.getDependencies().addAll((Collection<? extends Component>) newValue);
                return;
            case PapayaPackage.COMPONENT__USED_AS_DEPENDENCY_BY:
                this.getUsedAsDependencyBy().clear();
                this.getUsedAsDependencyBy().addAll((Collection<? extends Component>) newValue);
                return;
            case PapayaPackage.COMPONENT__COMPONENTS:
                this.getComponents().clear();
                this.getComponents().addAll((Collection<? extends Component>) newValue);
                return;
            case PapayaPackage.COMPONENT__PACKAGES:
                this.getPackages().clear();
                this.getPackages().addAll((Collection<? extends org.eclipse.sirius.components.papaya.Package>) newValue);
                return;
            case PapayaPackage.COMPONENT__PORTS:
                this.getPorts().clear();
                this.getPorts().addAll((Collection<? extends ComponentPort>) newValue);
                return;
            case PapayaPackage.COMPONENT__PROVIDED_SERVICES:
                this.getProvidedServices().clear();
                this.getProvidedServices().addAll((Collection<? extends ProvidedService>) newValue);
                return;
            case PapayaPackage.COMPONENT__REQUIRED_SERVICES:
                this.getRequiredServices().clear();
                this.getRequiredServices().addAll((Collection<? extends RequiredService>) newValue);
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
            case PapayaPackage.COMPONENT__DEPENDENCIES:
                this.getDependencies().clear();
                return;
            case PapayaPackage.COMPONENT__USED_AS_DEPENDENCY_BY:
                this.getUsedAsDependencyBy().clear();
                return;
            case PapayaPackage.COMPONENT__COMPONENTS:
                this.getComponents().clear();
                return;
            case PapayaPackage.COMPONENT__PACKAGES:
                this.getPackages().clear();
                return;
            case PapayaPackage.COMPONENT__PORTS:
                this.getPorts().clear();
                return;
            case PapayaPackage.COMPONENT__PROVIDED_SERVICES:
                this.getProvidedServices().clear();
                return;
            case PapayaPackage.COMPONENT__REQUIRED_SERVICES:
                this.getRequiredServices().clear();
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
            case PapayaPackage.COMPONENT__DEPENDENCIES:
                return this.dependencies != null && !this.dependencies.isEmpty();
            case PapayaPackage.COMPONENT__ALL_DEPENDENCIES:
                return !this.getAllDependencies().isEmpty();
            case PapayaPackage.COMPONENT__USED_AS_DEPENDENCY_BY:
                return this.usedAsDependencyBy != null && !this.usedAsDependencyBy.isEmpty();
            case PapayaPackage.COMPONENT__COMPONENTS:
                return this.components != null && !this.components.isEmpty();
            case PapayaPackage.COMPONENT__ALL_COMPONENTS:
                return !this.getAllComponents().isEmpty();
            case PapayaPackage.COMPONENT__PACKAGES:
                return this.packages != null && !this.packages.isEmpty();
            case PapayaPackage.COMPONENT__PORTS:
                return this.ports != null && !this.ports.isEmpty();
            case PapayaPackage.COMPONENT__PROVIDED_SERVICES:
                return this.providedServices != null && !this.providedServices.isEmpty();
            case PapayaPackage.COMPONENT__REQUIRED_SERVICES:
                return this.requiredServices != null && !this.requiredServices.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ComponentImpl
