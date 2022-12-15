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

    public static final UUID EMPTY_ID = UUID.nameUUIDFromBytes("empty".getBytes());

    public static final String EMPTY_LABEL = "Others...";

    public static final UUID EMPTY_FLOW_ID = UUID.nameUUIDFromBytes("empty_flow".getBytes());

    public static final String EMPTY_FLOW_LABEL = "Flow";

    public static final UUID ROBOT_FLOW_ID = UUID.nameUUIDFromBytes("robot_flow".getBytes());

    public static final String ROBOT_FLOW_LABEL = "Robot Flow";

    public static final UUID BIG_GUY_FLOW_ID = UUID.nameUUIDFromBytes("big_guy_flow".getBytes());

    public static final String BIG_GUY_FLOW_LABEL = "Big Guy Flow (17k elements)";

    private static final UUID EMPTY_DOMAIN_ID = UUID.nameUUIDFromBytes("empty_domain".getBytes());

    private static final String EMPTY_DOMAIN_LABEL = "Domain";

    private static final UUID EMPTY_VIEW_ID = UUID.nameUUIDFromBytes("empty_view".getBytes());

    private static final String EMPTY_VIEW_LABEL = "View";

    private static final String TIMER_NAME = "siriusweb_stereotype_load";

    private final StereotypeBuilder stereotypeBuilder;

    // @formatter:off
    private final List<String> sampleDomainNames = List.of(
            "agnesi",
            "albattani",
            "allen",
            "almeida",
            "antonelli",
            "archimedes",
            "ardinghelli",
            "aryabhata",
            "austin",
            "babbage",
            "banach",
            "banzai",
            "bardeen",
            "bartik",
            "bassi",
            "beaver",
            "bell",
            "benz",
            "bhabha",
            "bhaskara",
            "black",
            "blackburn",
            "blackwell",
            "bohr",
            "booth",
            "borg",
            "bose",
            "bouman",
            "boyd",
            "brahmagupta",
            "brattain",
            "brown",
            "buck",
            "burnell",
            "cannon",
            "carson",
            "cartwright",
            "carver",
            "cerf",
            "chandrasekhar",
            "chaplygin",
            "chatelet",
            "chatterjee",
            "chaum",
            "chebyshev",
            "clarke",
            "cohen",
            "colden",
            "cori",
            "cray",
            "curran",
            "curie",
            "darwin",
            "davinci",
            "dewdney",
            "dhawan",
            "diffie",
            "dijkstra",
            "dirac",
            "driscoll",
            "dubinsky",
            "easley",
            "edison",
            "einstein",
            "elbakyan",
            "elgamal",
            "elion",
            "ellis",
            "engelbart",
            "euclid",
            "euler",
            "faraday",
            "feistel",
            "fermat",
            "fermi",
            "feynman",
            "franklin",
            "gagarin",
            "galileo",
            "galois",
            "ganguly",
            "gates",
            "gauss",
            "germain",
            "goldberg",
            "goldstine",
            "goldwasser",
            "golick",
            "goodall",
            "gould",
            "greider",
            "grothendieck",
            "haibt",
            "hamilton",
            "haslett",
            "hawking",
            "hellman",
            "heisenberg",
            "hermann",
            "herschel",
            "hertz",
            "heyrovsky",
            "hodgkin",
            "hofstadter",
            "hoover",
            "hopper",
            "hugle",
            "hypatia",
            "ishizaka",
            "jackson",
            "jang",
            "jemison",
            "jennings",
            "jepsen",
            "johnson",
            "joliot",
            "jones",
            "kalam",
            "kapitsa",
            "kare",
            "keldysh",
            "keller",
            "kepler",
            "khayyam",
            "khorana",
            "kilby",
            "kirch",
            "knuth",
            "kowalevski",
            "lalande",
            "lamarr",
            "lamport",
            "leakey",
            "leavitt",
            "lederberg",
            "lehmann",
            "lewin",
            "lichterman",
            "liskov",
            "lovelace",
            "lumiere",
            "mahavira",
            "margulis",
            "matsumoto",
            "maxwell",
            "mayer",
            "mccarthy",
            "mcclintock",
            "mclaren",
            "mclean",
            "mcnulty",
            "mendel",
            "mendeleev",
            "meitner",
            "meninsky",
            "merkle",
            "mestorf",
            "mirzakhani",
            "montalcini",
            "moore",
            "morse",
            "murdock",
            "moser",
            "napier",
            "nash",
            "neumann",
            "newton",
            "nightingale",
            "nobel",
            "noether",
            "northcutt",
            "noyce",
            "panini",
            "pare",
            "pascal",
            "pasteur",
            "payne",
            "perlman",
            "pike",
            "poincare",
            "poitras",
            "proskuriakova",
            "ptolemy",
            "raman",
            "ramanujan",
            "ride",
            "ritchie",
            "rhodes",
            "robinson",
            "roentgen",
            "rosalind",
            "rubin",
            "saha",
            "sammet",
            "sanderson",
            "satoshi",
            "shamir",
            "shannon",
            "shaw",
            "shirley",
            "shockley",
            "shtern",
            "sinoussi",
            "snyder",
            "solomon",
            "spence",
            "stonebraker",
            "sutherland",
            "swanson",
            "swartz",
            "swirles",
            "taussig",
            "tereshkova",
            "tesla",
            "tharp",
            "thompson",
            "torvalds",
            "tu",
            "turing",
            "varahamihira",
            "vaughan",
            "villani",
            "visvesvaraya",
            "volhard",
            "wescoff",
            "wilbur",
            "wiles",
            "williams",
            "williamson",
            "wilson",
            "wing",
            "wozniak",
            "wright",
            "wu",
            "yalow",
            "yonath",
            "zhukovsky"
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
        registry.add(new StereotypeDescription(EMPTY_ID, EMPTY_LABEL, "New", this::getEmptyContent));
    }

    private String getEmptyDomainContent() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("SampleDomain");
        if (!this.sampleDomainNames.isEmpty()) {
            int randomIndex = this.random.nextInt(this.sampleDomainNames.size());
            domain.setName(this.sampleDomainNames.get(randomIndex));
        }
        return this.stereotypeBuilder.getStereotypeBody(domain);
    }

    private String getEmptyViewContent() {
        View newView = ViewFactory.eINSTANCE.createView();
        DiagramDescription diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("New Diagram Description");
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
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("robot.flow"));
    }

    private String getBigGuyFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("Big_Guy.flow"));
    }
}
