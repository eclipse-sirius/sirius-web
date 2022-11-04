/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.sample.configuration;

import fr.obeo.dsl.designer.sample.flow.FlowFactory;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register new stereotype descriptions.
 *
 * @author sbegaudeau
 */
@Configuration
public class StereotypeDescriptionRegistryConfigurer implements IStereotypeDescriptionRegistryConfigurer {

    public static final UUID EMPTY_ID = UUID.nameUUIDFromBytes("empty".getBytes()); //$NON-NLS-1$

    public static final String EMPTY_LABEL = "Others..."; //$NON-NLS-1$

    public static final UUID EMPTY_FLOW_ID = UUID.nameUUIDFromBytes("empty_flow".getBytes()); //$NON-NLS-1$

    public static final String EMPTY_FLOW_LABEL = "Flow"; //$NON-NLS-1$

    public static final UUID ROBOT_FLOW_ID = UUID.nameUUIDFromBytes("robot_flow".getBytes()); //$NON-NLS-1$

    public static final String ROBOT_FLOW_LABEL = "Robot Flow"; //$NON-NLS-1$

    public static final UUID BIG_GUY_FLOW_ID = UUID.nameUUIDFromBytes("big_guy_flow".getBytes()); //$NON-NLS-1$

    public static final String BIG_GUY_FLOW_LABEL = "Big Guy Flow (17k elements)"; //$NON-NLS-1$

    private static final UUID EMPTY_DOMAIN_ID = UUID.nameUUIDFromBytes("empty_domain".getBytes()); //$NON-NLS-1$

    private static final String EMPTY_DOMAIN_LABEL = "Domain"; //$NON-NLS-1$

    private static final UUID EMPTY_VIEW_ID = UUID.nameUUIDFromBytes("empty_view".getBytes()); //$NON-NLS-1$

    private static final String EMPTY_VIEW_LABEL = "View"; //$NON-NLS-1$

    private static final String TIMER_NAME = "siriusweb_stereotype_load"; //$NON-NLS-1$

    private final StereotypeBuilder stereotypeBuilder;

    // @formatter:off
    private final List<String> sampleDomainNames = List.of(
            "agnesi", //$NON-NLS-1$
            "albattani", //$NON-NLS-1$
            "allen", //$NON-NLS-1$
            "almeida", //$NON-NLS-1$
            "antonelli", //$NON-NLS-1$
            "archimedes", //$NON-NLS-1$
            "ardinghelli", //$NON-NLS-1$
            "aryabhata", //$NON-NLS-1$
            "austin", //$NON-NLS-1$
            "babbage", //$NON-NLS-1$
            "banach", //$NON-NLS-1$
            "banzai", //$NON-NLS-1$
            "bardeen", //$NON-NLS-1$
            "bartik", //$NON-NLS-1$
            "bassi", //$NON-NLS-1$
            "beaver", //$NON-NLS-1$
            "bell", //$NON-NLS-1$
            "benz", //$NON-NLS-1$
            "bhabha", //$NON-NLS-1$
            "bhaskara", //$NON-NLS-1$
            "black", //$NON-NLS-1$
            "blackburn", //$NON-NLS-1$
            "blackwell", //$NON-NLS-1$
            "bohr", //$NON-NLS-1$
            "booth", //$NON-NLS-1$
            "borg", //$NON-NLS-1$
            "bose", //$NON-NLS-1$
            "bouman", //$NON-NLS-1$
            "boyd", //$NON-NLS-1$
            "brahmagupta", //$NON-NLS-1$
            "brattain", //$NON-NLS-1$
            "brown", //$NON-NLS-1$
            "buck", //$NON-NLS-1$
            "burnell", //$NON-NLS-1$
            "cannon", //$NON-NLS-1$
            "carson", //$NON-NLS-1$
            "cartwright", //$NON-NLS-1$
            "carver", //$NON-NLS-1$
            "cerf", //$NON-NLS-1$
            "chandrasekhar", //$NON-NLS-1$
            "chaplygin", //$NON-NLS-1$
            "chatelet", //$NON-NLS-1$
            "chatterjee", //$NON-NLS-1$
            "chaum", //$NON-NLS-1$
            "chebyshev", //$NON-NLS-1$
            "clarke", //$NON-NLS-1$
            "cohen", //$NON-NLS-1$
            "colden", //$NON-NLS-1$
            "cori", //$NON-NLS-1$
            "cray", //$NON-NLS-1$
            "curran", //$NON-NLS-1$
            "curie", //$NON-NLS-1$
            "darwin", //$NON-NLS-1$
            "davinci", //$NON-NLS-1$
            "dewdney", //$NON-NLS-1$
            "dhawan", //$NON-NLS-1$
            "diffie", //$NON-NLS-1$
            "dijkstra", //$NON-NLS-1$
            "dirac", //$NON-NLS-1$
            "driscoll", //$NON-NLS-1$
            "dubinsky", //$NON-NLS-1$
            "easley", //$NON-NLS-1$
            "edison", //$NON-NLS-1$
            "einstein", //$NON-NLS-1$
            "elbakyan", //$NON-NLS-1$
            "elgamal", //$NON-NLS-1$
            "elion", //$NON-NLS-1$
            "ellis", //$NON-NLS-1$
            "engelbart", //$NON-NLS-1$
            "euclid", //$NON-NLS-1$
            "euler", //$NON-NLS-1$
            "faraday", //$NON-NLS-1$
            "feistel", //$NON-NLS-1$
            "fermat", //$NON-NLS-1$
            "fermi", //$NON-NLS-1$
            "feynman", //$NON-NLS-1$
            "franklin", //$NON-NLS-1$
            "gagarin", //$NON-NLS-1$
            "galileo", //$NON-NLS-1$
            "galois", //$NON-NLS-1$
            "ganguly", //$NON-NLS-1$
            "gates", //$NON-NLS-1$
            "gauss", //$NON-NLS-1$
            "germain", //$NON-NLS-1$
            "goldberg", //$NON-NLS-1$
            "goldstine", //$NON-NLS-1$
            "goldwasser", //$NON-NLS-1$
            "golick", //$NON-NLS-1$
            "goodall", //$NON-NLS-1$
            "gould", //$NON-NLS-1$
            "greider", //$NON-NLS-1$
            "grothendieck", //$NON-NLS-1$
            "haibt", //$NON-NLS-1$
            "hamilton", //$NON-NLS-1$
            "haslett", //$NON-NLS-1$
            "hawking", //$NON-NLS-1$
            "hellman", //$NON-NLS-1$
            "heisenberg", //$NON-NLS-1$
            "hermann", //$NON-NLS-1$
            "herschel", //$NON-NLS-1$
            "hertz", //$NON-NLS-1$
            "heyrovsky", //$NON-NLS-1$
            "hodgkin", //$NON-NLS-1$
            "hofstadter", //$NON-NLS-1$
            "hoover", //$NON-NLS-1$
            "hopper", //$NON-NLS-1$
            "hugle", //$NON-NLS-1$
            "hypatia", //$NON-NLS-1$
            "ishizaka", //$NON-NLS-1$
            "jackson", //$NON-NLS-1$
            "jang", //$NON-NLS-1$
            "jemison", //$NON-NLS-1$
            "jennings", //$NON-NLS-1$
            "jepsen", //$NON-NLS-1$
            "johnson", //$NON-NLS-1$
            "joliot", //$NON-NLS-1$
            "jones", //$NON-NLS-1$
            "kalam", //$NON-NLS-1$
            "kapitsa", //$NON-NLS-1$
            "kare", //$NON-NLS-1$
            "keldysh", //$NON-NLS-1$
            "keller", //$NON-NLS-1$
            "kepler", //$NON-NLS-1$
            "khayyam", //$NON-NLS-1$
            "khorana", //$NON-NLS-1$
            "kilby", //$NON-NLS-1$
            "kirch", //$NON-NLS-1$
            "knuth", //$NON-NLS-1$
            "kowalevski", //$NON-NLS-1$
            "lalande", //$NON-NLS-1$
            "lamarr", //$NON-NLS-1$
            "lamport", //$NON-NLS-1$
            "leakey", //$NON-NLS-1$
            "leavitt", //$NON-NLS-1$
            "lederberg", //$NON-NLS-1$
            "lehmann", //$NON-NLS-1$
            "lewin", //$NON-NLS-1$
            "lichterman", //$NON-NLS-1$
            "liskov", //$NON-NLS-1$
            "lovelace", //$NON-NLS-1$
            "lumiere", //$NON-NLS-1$
            "mahavira", //$NON-NLS-1$
            "margulis", //$NON-NLS-1$
            "matsumoto", //$NON-NLS-1$
            "maxwell", //$NON-NLS-1$
            "mayer", //$NON-NLS-1$
            "mccarthy", //$NON-NLS-1$
            "mcclintock", //$NON-NLS-1$
            "mclaren", //$NON-NLS-1$
            "mclean", //$NON-NLS-1$
            "mcnulty", //$NON-NLS-1$
            "mendel", //$NON-NLS-1$
            "mendeleev", //$NON-NLS-1$
            "meitner", //$NON-NLS-1$
            "meninsky", //$NON-NLS-1$
            "merkle", //$NON-NLS-1$
            "mestorf", //$NON-NLS-1$
            "mirzakhani", //$NON-NLS-1$
            "montalcini", //$NON-NLS-1$
            "moore", //$NON-NLS-1$
            "morse", //$NON-NLS-1$
            "murdock", //$NON-NLS-1$
            "moser", //$NON-NLS-1$
            "napier", //$NON-NLS-1$
            "nash", //$NON-NLS-1$
            "neumann", //$NON-NLS-1$
            "newton", //$NON-NLS-1$
            "nightingale", //$NON-NLS-1$
            "nobel", //$NON-NLS-1$
            "noether", //$NON-NLS-1$
            "northcutt", //$NON-NLS-1$
            "noyce", //$NON-NLS-1$
            "panini", //$NON-NLS-1$
            "pare", //$NON-NLS-1$
            "pascal", //$NON-NLS-1$
            "pasteur", //$NON-NLS-1$
            "payne", //$NON-NLS-1$
            "perlman", //$NON-NLS-1$
            "pike", //$NON-NLS-1$
            "poincare", //$NON-NLS-1$
            "poitras", //$NON-NLS-1$
            "proskuriakova", //$NON-NLS-1$
            "ptolemy", //$NON-NLS-1$
            "raman", //$NON-NLS-1$
            "ramanujan", //$NON-NLS-1$
            "ride", //$NON-NLS-1$
            "ritchie", //$NON-NLS-1$
            "rhodes", //$NON-NLS-1$
            "robinson", //$NON-NLS-1$
            "roentgen", //$NON-NLS-1$
            "rosalind", //$NON-NLS-1$
            "rubin", //$NON-NLS-1$
            "saha", //$NON-NLS-1$
            "sammet", //$NON-NLS-1$
            "sanderson", //$NON-NLS-1$
            "satoshi", //$NON-NLS-1$
            "shamir", //$NON-NLS-1$
            "shannon", //$NON-NLS-1$
            "shaw", //$NON-NLS-1$
            "shirley", //$NON-NLS-1$
            "shockley", //$NON-NLS-1$
            "shtern", //$NON-NLS-1$
            "sinoussi", //$NON-NLS-1$
            "snyder", //$NON-NLS-1$
            "solomon", //$NON-NLS-1$
            "spence", //$NON-NLS-1$
            "stonebraker", //$NON-NLS-1$
            "sutherland", //$NON-NLS-1$
            "swanson", //$NON-NLS-1$
            "swartz", //$NON-NLS-1$
            "swirles", //$NON-NLS-1$
            "taussig", //$NON-NLS-1$
            "tereshkova", //$NON-NLS-1$
            "tesla", //$NON-NLS-1$
            "tharp", //$NON-NLS-1$
            "thompson", //$NON-NLS-1$
            "torvalds", //$NON-NLS-1$
            "tu", //$NON-NLS-1$
            "turing", //$NON-NLS-1$
            "varahamihira", //$NON-NLS-1$
            "vaughan", //$NON-NLS-1$
            "villani", //$NON-NLS-1$
            "visvesvaraya", //$NON-NLS-1$
            "volhard", //$NON-NLS-1$
            "wescoff", //$NON-NLS-1$
            "wilbur", //$NON-NLS-1$
            "wiles", //$NON-NLS-1$
            "williams", //$NON-NLS-1$
            "williamson", //$NON-NLS-1$
            "wilson", //$NON-NLS-1$
            "wing", //$NON-NLS-1$
            "wozniak", //$NON-NLS-1$
            "wright", //$NON-NLS-1$
            "wu", //$NON-NLS-1$
            "yalow", //$NON-NLS-1$
            "yonath", //$NON-NLS-1$
            "zhukovsky" //$NON-NLS-1$
    );
    // @formatter:on

    private final Random random;

    private final boolean studiosEnabled;

    public StereotypeDescriptionRegistryConfigurer(MeterRegistry meterRegistry, @Value("${org.eclipse.sirius.web.features.studioDefinition:false}") boolean studiosEnabled) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
        this.studiosEnabled = studiosEnabled;
        this.random = new Random();
    }

    @Override
    public void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry) {
        registry.add(new StereotypeDescription(EMPTY_FLOW_ID, EMPTY_FLOW_LABEL, this::getEmptyFlowContent));
        if (this.studiosEnabled) {
            registry.add(new StereotypeDescription(EMPTY_DOMAIN_ID, EMPTY_DOMAIN_LABEL, this::getEmptyDomainContent));
            registry.add(new StereotypeDescription(EMPTY_VIEW_ID, EMPTY_VIEW_LABEL, this::getEmptyViewContent));
        }
        registry.add(new StereotypeDescription(ROBOT_FLOW_ID, ROBOT_FLOW_LABEL, this::getRobotFlowContent));
        registry.add(new StereotypeDescription(BIG_GUY_FLOW_ID, BIG_GUY_FLOW_LABEL, this::getBigGuyFlowContent));
        registry.add(new StereotypeDescription(EMPTY_ID, EMPTY_LABEL, "New", this::getEmptyContent)); //$NON-NLS-1$
    }

    private String getEmptyDomainContent() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("SampleDomain"); //$NON-NLS-1$
        if (!this.sampleDomainNames.isEmpty()) {
            int randomIndex = this.random.nextInt(this.sampleDomainNames.size());
            domain.setName(this.sampleDomainNames.get(randomIndex));
        }
        return this.stereotypeBuilder.getStereotypeBody(domain);
    }

    private String getEmptyViewContent() {
        View newView = ViewFactory.eINSTANCE.createView();
        DiagramDescription diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("New Diagram Description"); //$NON-NLS-1$
        newView.getDescriptions().add(diagramDescription);
        return this.stereotypeBuilder.getStereotypeBody(newView);
    }

    private String getEmptyContent() {
        return this.stereotypeBuilder.getStereotypeBody((EObject) null);
    }

    private String getEmptyFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(FlowFactory.eINSTANCE.createSystem());
    }

    private String getRobotFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("robot.flow")); //$NON-NLS-1$
    }

    private String getBigGuyFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("Big_Guy.flow")); //$NON-NLS-1$
    }
}
