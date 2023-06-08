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
package org.eclipse.sirius.components.view.builder.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An object of the selection.
 *
 * @author cbrun
 */
public class BuilderGenerator {
    private static final String CLASSBODY = "#classbody";

    private static final String PACKAGE = "#package";

    private static final String BUILDER_CLASSNAME = "#builderClassName";

    private static final String BUILDER_EOBJECT_NAME = "#eObjName";

    private static final Logger LOGGER = LoggerFactory.getLogger(BuilderGenerator.class);

    private String pathToJMergeFile;

    private String outputDirectory;

    private String basePackage = "fr.obeo.dsl.biblio.app.representationsbuilders.builders.test";

    public BuilderGenerator(String pathToJMergeFile, String outputDirectory, String basePackage) {
        this.pathToJMergeFile = pathToJMergeFile;
        this.outputDirectory = outputDirectory;
        this.basePackage = basePackage;
    }

    public static void main(String[] args) {

        for (String string : args) {
            LOGGER.error(string);
        }
        String javaVersion = Runtime.version().toString();
        String time = LocalDateTime.now().toString();
        LOGGER.info("********\nBuild Time: " + time + "\nJava Version: " + javaVersion + "\n********");

        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new EcoreResourceFactoryImpl());

        resourceSet.getPackageRegistry().put(GenModelPackage.eNS_URI, GenModelPackage.eINSTANCE);
        resourceSet.getResources().add(EcorePackage.eINSTANCE.eResource());
        resourceSet.getURIConverter().getURIMap().put(URI.createURI("platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore"), URI.createURI("http://www.eclipse.org/emf/2002/Ecore"));

        URI uri = URI.createURI("file://" + args[1]);
        Resource g = resourceSet.getResource(uri, true);
        BuilderGenerator gen = new BuilderGenerator(args[0], args[2], args[3]);

        StreamSupport.stream(Spliterators.spliterator(g.getContents(), Spliterator.ORDERED), false).filter(GenModel.class::isInstance).map(GenModel.class::cast).forEach(model -> {
            try {
                gen.doGen(model);
            } catch (IOException e1) {
                LOGGER.error(e1.getMessage());
            }
        });
        LOGGER.info("Generated!");
    }

    private StringBuffer getFactoryClassBody(GenPackage pak) {
        StringBuffer classBodies = new StringBuffer();
        for (GenClass clazz : pak.getGenClasses()) {
            if (!clazz.isAbstract() && !clazz.isInterface() && clazz.hasFactoryInterfaceCreateMethod()) {
                classBodies.append("""
                            /**
                             * Instantiate a #builderClassName .
                             *
                             * @author BuilderGenerator
                             * @generated
                             */
                            public #builderClassName new#className() {
                                return new #builderClassName();
                            }

                        """.replace(BUILDER_CLASSNAME, this.builderClasssName(clazz))
                        .replace("#className", clazz.getName()));
            }
        }
        return classBodies;
    }

    private StringBuffer getFactory(GenPackage pak, String builderFactoryName, StringBuffer factoryClassBody) {
        StringBuffer factory = new StringBuffer();
        factory.append("""
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
                package #package;

                /**
                 * Use to instantiate a new builder.
                 *
                 * @author BuilderGenerator
                 * @generated
                 */
                public class #factoryName {
                #classbody
                }
                """.replace("#factoryName", builderFactoryName).replace(CLASSBODY, factoryClassBody).replace(PACKAGE, this.getPackageDeclaration(pak)));
        return factory;
    }

    private StringBuffer getBody(GenClass clazz) {
        StringBuffer body = new StringBuffer();
        if (!clazz.isAbstract()) {
            body.append("""
                        /**
                         * Create instance #eObjType.
                         * @generated
                         */
                        private #eObjType #eObjNameLowerCase = #packageFactory.create#eObjName();

                        /**
                         * Return instance #eObjType.
                         * @generated
                         */
                        protected #eObjType get#eObjName() {
                            return this.#eObjNameLowerCase;
                        }

                        /**
                         * Return instance #eObjType.
                         * @generated
                         */
                        public #eObjType build() {
                            return this.get#eObjName();
                        }

                    """.replace("#packageFactory", clazz.getGenPackage().getQualifiedEFactoryInternalInstanceAccessor()).replace("#eObjType", clazz.getQualifiedInterfaceName())
                    .replace("#eObjNameLowerCase", clazz.uncapPrefixedName(clazz.getSafeUncapName())).replace(BUILDER_EOBJECT_NAME, clazz.capName(clazz.getSafeUncapName())));
        }

        for (GenFeature feat : clazz.getAllGenFeatures()) {

            if (!feat.getEcoreFeature().isDerived() && feat.getEcoreFeature().isChangeable()) {
                if (!feat.getEcoreFeature().isMany()) {
                    body.append("""
                                /**
                                 * Setter for #accessor.
                                 *#javadoc
                                 * @generated
                                 */
                                public #builderClassName #featName(#paramType value) {
                                    this.get#eObjName().set#accessor(value);
                                    return this;
                                }
                            """.replace(BUILDER_CLASSNAME, this.builderClasssName(clazz)).replace("#paramType", feat.getListItemType(clazz)).replace("#accessor", feat.getAccessorName())
                            .replace("#featName", feat.getSafeName()).replace("#javadoc", this.getDocumentation(feat)).replace(BUILDER_EOBJECT_NAME, clazz.capName(clazz.getSafeUncapName())));
                } else {
                    body.append("""
                                /**
                                 * Setter for #accessor.
                                 *#javadoc
                                 * @generated
                                 */
                                public #builderClassName #featName(#paramType ... values) {
                                    for (#paramType value : values) {
                                        this.get#eObjName().get#accessor().add(value);
                                    }
                                    return this;
                                }

                            """.replace(BUILDER_CLASSNAME, this.builderClasssName(clazz)).replace("#paramType", feat.getListItemType(clazz)).replace("#accessor", feat.getAccessorName())
                            .replace("#featName", feat.getSafeName()).replace("#javadoc", this.getDocumentation(feat)).replace(BUILDER_EOBJECT_NAME, clazz.capName(clazz.getSafeUncapName())));
                }
            }
        }
        return body;

    }

    private StringBuffer getBuilder(GenPackage pak, GenClass clazz, StringBuffer body) {
        StringBuffer builder = new StringBuffer();
        if (clazz.isAbstract()) {
            builder.append("""
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
                    package #package;

                    /**
                     * Builder for #qualifiedType.
                     *
                     * @author BuilderGenerator
                     * @generated
                     */
                    public abstract class #builderClassName {

                        /**
                         * Builder for #qualifiedType.
                         * @generated
                         */
                        protected abstract #qualifiedType get#eObjName();

                    #classbody
                    }

                    """.replace(BUILDER_CLASSNAME, this.builderClasssName(clazz)).replace("#qualifiedType", this.qualifiedNameFromGenClass(clazz)).replace(CLASSBODY, body).replace(PACKAGE,
                    this.getPackageDeclaration(pak)).replace(BUILDER_EOBJECT_NAME, clazz.capName(clazz.getSafeUncapName())));
        } else {
            builder.append("""
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
                    package #package;

                    /**
                     * Builder for #builderClassName.
                     *
                     * @author BuilderGenerator
                     * @generated
                     */
                    public class #builderClassName {

                    #classbody
                    }

                    """.replace(BUILDER_CLASSNAME, this.builderClasssName(clazz)).replace("#qualifiedType", clazz.getQualifiedInterfaceName()).replace(CLASSBODY, body).replace(PACKAGE,
                    this.getPackageDeclaration(pak)));
        }
        return builder;
    }

    public void doGen(GenModel model) throws IOException {
        JControlModel jControlModel = new JControlModel();
        jControlModel.setConvertToStandardBraceStyle(true);
        ASTFacadeHelper facadeHelper = new ASTFacadeHelper();
        facadeHelper.setCompilerCompliance("17");
        String jmergeFile = this.pathToJMergeFile;
        if (jmergeFile == null) {
            // emf-merge file is from org.eclipse.emf.codegen.ecore/templates/emf-merge.xml
            this.getClass().getResource("emf-merge.xml").toString();
        }
        jControlModel.initialize(facadeHelper, jmergeFile);

        for (GenPackage pak : model.getGenPackages()) {
            StringBuffer factory = new StringBuffer();
            String builderFactoryName = pak.getPrefix() + "Builders";

            StringBuffer factoryClassBody = new StringBuffer();
            factoryClassBody = this.getFactoryClassBody(pak);
            factory = this.getFactory(pak, builderFactoryName, factoryClassBody);

            String factFileName = builderFactoryName + ".java";
            this.generateOrMerge(jControlModel, this.outputDirectory, factFileName, factory.toString());

            for (GenClass clazz : pak.getGenClasses()) {

                StringBuffer builderBody = new StringBuffer();
                builderBody = this.getBody(clazz);

                StringBuffer builder = new StringBuffer();
                builder = this.getBuilder(pak, clazz, builderBody);

                String fileName = this.builderClasssName(clazz) + ".java";
                String contentToGenerate = builder.toString();
                this.generateOrMerge(jControlModel, this.outputDirectory, fileName, contentToGenerate);
            }
        }

    }

    private String qualifiedNameFromGenClass(GenClass clazz) {
        return clazz.getTypeParameters() + clazz.getImportedInterfaceName() + clazz.getInterfaceTypeArguments();
    }

    private String getPackageDeclaration(GenPackage pak) {
        return this.basePackage;
    }

    private String getDocumentation(GenFeature feat) {
        if (feat.getDocumentation() != null) {
            return " " + feat.getDocumentation();
        }
        return "";
    }

    private void generateOrMerge(JControlModel jControlModel, String outDirectory, String fileName, String contentToGenerate) throws IOException {
        String beforeGen = null;
        if (Files.exists(Path.of(outDirectory, fileName))) {
            beforeGen = Files.readString(Path.of(outDirectory, fileName));
        } else {
            Files.createDirectories(Path.of(outDirectory));
        }
        if (beforeGen != null && jControlModel.canMerge()) {
            JMerger jMerger = new JMerger(jControlModel);
            jMerger.setSourceCompilationUnit(contentToGenerate);
            jMerger.setTargetCompilationUnit(jMerger.createCompilationUnitForContents(beforeGen));
            jMerger.merge();
            String mergedContent = jMerger.getTargetCompilationUnit().getContents();
            LOGGER.info("MERGED " + Path.of(outDirectory, fileName));
            Files.writeString(Path.of(outDirectory, fileName), mergedContent);
        } else {
            LOGGER.info("NEW " + Path.of(this.outputDirectory, fileName));
            Files.writeString(Path.of(outDirectory, fileName), contentToGenerate, StandardOpenOption.CREATE);
        }
    }

    private String builderClasssName(GenClass clazz) {
        return clazz.getName() + "Builder";
    }
}
