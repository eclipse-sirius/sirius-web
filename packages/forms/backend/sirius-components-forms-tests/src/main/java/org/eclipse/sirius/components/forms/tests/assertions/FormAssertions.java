/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.forms.tests.assertions;

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.DateTime;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Link;
import org.eclipse.sirius.components.forms.MultiSelect;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.forms.RichText;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.Slider;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;

/**
 * Entry point of all the AssertJ assertions with the form specific ones too.
 *
 * @author lfasani
 */
public class FormAssertions extends Assertions {
    public static FormAssert assertThat(Form form) {
        return new FormAssert(form);
    }

    public static PageAssert assertThat(Page page) {
        return new PageAssert(page);
    }

    public static GroupAssert assertThat(Group group) {
        return new GroupAssert(group);
    }

    public static CheckboxAssert assertThat(Checkbox checkbox) {
        return new CheckboxAssert(checkbox);
    }

    public static LinkAssert assertThat(Link link) {
        return new LinkAssert(link);
    }

    public static MultiSelectAssert assertThat(MultiSelect multiSelect) {
        return new MultiSelectAssert(multiSelect);
    }

    public static ReferenceWidgetAssert assertThat(ReferenceWidget referenceWidget) {
        return new ReferenceWidgetAssert(referenceWidget);
    }

    public static RichTextAssert assertThat(RichText richText) {
        return new RichTextAssert(richText);
    }

    public static SelectAssert assertThat(Select select) {
        return new SelectAssert(select);
    }

    public static SliderAssert assertThat(Slider slider) {
        return new SliderAssert(slider);
    }

    public static DateTimeAssert assertThat(DateTime dateTime) {
        return new DateTimeAssert(dateTime);
    }

    public static TextareaAssert assertThat(Textarea textarea) {
        return new TextareaAssert(textarea);
    }

    public static TextfieldAssert assertThat(Textfield textfield) {
        return new TextfieldAssert(textfield);
    }

    public static TreeWidgetAssert assertThat(TreeWidget treeWidget) {
        return new TreeWidgetAssert(treeWidget);
    }

}
