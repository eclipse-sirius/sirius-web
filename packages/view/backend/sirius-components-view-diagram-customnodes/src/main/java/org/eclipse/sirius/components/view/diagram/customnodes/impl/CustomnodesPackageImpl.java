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
package org.eclipse.sirius.components.view.diagram.customnodes.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesFactory;
import org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesPackage;
import org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class CustomnodesPackageImpl extends EPackageImpl implements CustomnodesPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass ellipseNodeStyleDescriptionEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private CustomnodesPackageImpl() {
        super(eNS_URI, CustomnodesFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link CustomnodesPackage#eINSTANCE} when that field is accessed. Clients
     * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static CustomnodesPackage init() {
        if (isInited)
            return (CustomnodesPackage) EPackage.Registry.INSTANCE.getEPackage(CustomnodesPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredCustomnodesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        CustomnodesPackageImpl theCustomnodesPackage = registeredCustomnodesPackage instanceof CustomnodesPackageImpl ? (CustomnodesPackageImpl) registeredCustomnodesPackage
                : new CustomnodesPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        DiagramPackage.eINSTANCE.eClass();
        ViewPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theCustomnodesPackage.createPackageContents();

        // Initialize created meta-data
        theCustomnodesPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theCustomnodesPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(CustomnodesPackage.eNS_URI, theCustomnodesPackage);
        return theCustomnodesPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEllipseNodeStyleDescription() {
        return this.ellipseNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CustomnodesFactory getCustomnodesFactory() {
        return (CustomnodesFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.ellipseNodeStyleDescriptionEClass = this.createEClass(ELLIPSE_NODE_STYLE_DESCRIPTION);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        DiagramPackage theDiagramPackage = (DiagramPackage) EPackage.Registry.INSTANCE.getEPackage(DiagramPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.ellipseNodeStyleDescriptionEClass.getESuperTypes().add(theDiagramPackage.getNodeStyleDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.ellipseNodeStyleDescriptionEClass, EllipseNodeStyleDescription.class, "EllipseNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // CustomnodesPackageImpl
