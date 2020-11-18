/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
package org.eclipse.sirius.web.freediagram.flow.behaviors;

import fr.obeo.dsl.designer.sample.flow.DataFlow;
import fr.obeo.dsl.designer.sample.flow.FlowPackage;
import fr.obeo.dsl.designer.sample.flow.FlowSource;
import fr.obeo.dsl.designer.sample.flow.FlowTarget;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.tools.CreateEdgeTool;
import org.eclipse.sirius.web.freediagram.behaviors.ICreateInstanceBehavior;
import org.eclipse.sirius.web.freediagram.behaviors.IEdgeMappingBehavior;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * DataFlow relationship behaviors.
 *
 * Define the create instance behavior.
 *
 * @author hmarchadour
 */
public class DataFlowBehavior implements IEdgeMappingBehavior, ICreateInstanceBehavior {

    @Override
    public List<Object> getSourceElements(VariableManager variableManager) {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, DataFlow.class)
            .map(DataFlow::getSource)
            .map(Object.class::cast)
            .map(List::of)
            .orElse(List.of());
        // @formatter:on
    }

    @Override
    public List<Object> getTargetElements(VariableManager variableManager) {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, DataFlow.class)
            .map(DataFlow::getTarget)
            .map(Object.class::cast)
            .map(List::of)
            .orElse(List.of());
        // @formatter:on
    }

    @Override
    public List<Object> getSemanticElements(VariableManager variableManager) {
     // @formatter:off
        return variableManager.get(DiagramDescription.TARGET_SEMANTIC_OBJECTS, Map.class)
           .map(targetSemanticObjectsMap -> {
               Iterable<Object> it = () -> targetSemanticObjectsMap.values().iterator();

               Stream<Object> a = StreamSupport.stream(it.spliterator(), false)
                   .filter(FlowSource.class::isInstance)
                   .map(FlowSource.class::cast)
                   .map(FlowSource::getOutgoingFlows)
                   .flatMap(List::stream);

               Stream<Object> b = StreamSupport.stream(it.spliterator(), false)
                   .filter(FlowTarget.class::isInstance)
                   .map(FlowTarget.class::cast)
                   .map(FlowTarget::getIncomingFlows)
                   .flatMap(List::stream);

           return Stream.concat(a, b)
                   .distinct()
                   .collect(Collectors.toList());
           }).orElse(List.of());
       // @formatter:on
    }

    @Override
    public Optional<EObject> getOwner(VariableManager variableManager) {
        Optional<FlowSource> optionalFlowSource = variableManager.get(CreateEdgeTool.EDGE_SOURCE, FlowSource.class);
        if (optionalFlowSource.isPresent()) {
            return optionalFlowSource.map(EObject.class::cast);
        } else {
            return variableManager.get(CreateEdgeTool.EDGE_TARGET, FlowSource.class).map(EObject.class::cast);
        }
    }

    @Override
    public Optional<EReference> getContainmentFeature(VariableManager variableManager) {
        return Optional.of(FlowPackage.Literals.FLOW_SOURCE__OUTGOING_FLOWS);
    }

    @Override
    public Optional<EObject> createEObject(VariableManager variableManager) {
        DataFlow instance = (DataFlow) EcoreUtil.create(FlowPackage.Literals.DATA_FLOW);
        Optional<FlowTarget> optionalTarget = variableManager.get(CreateEdgeTool.EDGE_TARGET, FlowTarget.class);
        if (optionalTarget.isPresent()) {
            instance.setTarget(optionalTarget.get());
        }
        return Optional.of(instance);
    }
}
